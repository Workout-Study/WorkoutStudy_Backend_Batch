package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.dto.quartz.*
import com.fitmate.batchservice.exception.BadRequestException
import com.fitmate.batchservice.exception.NotExpectResultException
import com.fitmate.batchservice.exception.ResourceNotFoundException
import com.fitmate.batchservice.persistence.entity.JobScheduler
import com.fitmate.batchservice.persistence.repository.JobSchedulerRepository
import com.fitmate.batchservice.scheduler.QuartzJob
import com.fitmate.batchservice.scheduler.QuartzListener
import org.quartz.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuartzServiceImpl(
    schedulerFactoryBean: SchedulerFactoryBean,
    private val jobSchedulerRepository: JobSchedulerRepository,
    private val jobLauncher: JobLauncher,
    private val jobLocator: JobLocator
) : QuartzService {

    private val scheduler: Scheduler = schedulerFactoryBean.scheduler

    companion object {
        val logger: Logger = LoggerFactory.getLogger(QuartzListener::class.java)
    }

    override fun register() {
        val jobSchedulers: List<JobScheduler> = jobSchedulerRepository.findAll()

        jobSchedulers.forEach {
            if (!it.isDeleted && isValidCronExpression(it.cron)) {
                addJob(it.jobName, it.cron)

                if (it.pause) scheduler.pauseJob(JobKey.jobKey(it.jobName))
            }
        }
    }

    override fun start() {
        if (!scheduler.isStarted) scheduler.start()
    }

    override fun addListener(jobListener: QuartzListener) =
        scheduler.listenerManager.addJobListener(jobListener)


    override fun shutdown() {
        if (!scheduler.isShutdown) scheduler.shutdown()
    }

    override fun clear() = scheduler.clear()

    @Transactional
    override fun registerJob(registerQuartzRequestDto: RegisterQuartzRequestDto): RegisterQuartzResponseDto {
        val jobKey = JobKey.jobKey(registerQuartzRequestDto.jobName)
        try {
            if (scheduler.checkExists(jobKey)) throw BadRequestException("quartz.job.already exist")
        } catch (e: SchedulerException) {
            logger.error("registerJob error!!", e)
            throw BadRequestException("quartz.job.SchedulerException = {${e}}")
        }

        jobSchedulerRepository.findByJobName(registerQuartzRequestDto.jobName)
            .ifPresentOrElse(
                { it.updateByRegisterDto(registerQuartzRequestDto) },
                {
                    val newJobScheduler = JobScheduler(
                        registerQuartzRequestDto.jobName,
                        registerQuartzRequestDto.cron,
                        createUser = registerQuartzRequestDto.requestUserId
                    )

                    jobSchedulerRepository.save(newJobScheduler)
                }
            )

        addJob(registerQuartzRequestDto.jobName, registerQuartzRequestDto.cron)

        return RegisterQuartzResponseDto(true)
    }

    @Transactional
    override fun updateJob(jobName: String, updateQuartzRequestDto: UpdateQuartzRequestDto): UpdateQuartzResponseDto {
        val jobKey = JobKey.jobKey(jobName)
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw BadRequestException("quartz.job.NotFound")
            }

            scheduler.deleteJob(jobKey)
        } catch (e: SchedulerException) {
            logger.error("updateJob error!!", e)
            throw NotExpectResultException("quartz.job.SchedulerException = {${e}}")
        }

        val jobScheduler = jobSchedulerRepository.findByJobName(jobName)
            .orElseThrow { ResourceNotFoundException("job scheduler does not exist") }

        if (jobScheduler.isDeleted) throw BadRequestException("job scheduler already deleted")

        addJob(jobName, updateQuartzRequestDto.cron)

        jobScheduler.updateByUpdateDto(updateQuartzRequestDto)
        jobSchedulerRepository.save(jobScheduler)

        return UpdateQuartzResponseDto(true)
    }

    @Transactional
    override fun deleteJob(jobName: String, deleteQuartzRequestDto: DeleteQuartzRequestDto): DeleteQuartzResponseDto {
        val jobKey = JobKey.jobKey(jobName)
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw BadRequestException("quartz.job.NotFound")
            }

            scheduler.deleteJob(jobKey)
        } catch (e: SchedulerException) {
            logger.error("deleteJob error!!", e)
            throw NotExpectResultException("quartz.job.SchedulerException = {${e}}")
        }

        val jobScheduler = jobSchedulerRepository.findByJobName(jobName)
            .orElseThrow { ResourceNotFoundException("job scheduler does not exist") }

        if (jobScheduler.isDeleted) throw BadRequestException("job scheduler already deleted")

        jobScheduler.pause = GlobalStatus.SCHEDULER_STATUS_PAUSE
        jobScheduler.delete()

        jobSchedulerRepository.save(jobScheduler)

        return DeleteQuartzResponseDto(true)
    }

    @Transactional
    override fun pauseJob(jobName: String, pauseQuartzRequestDto: PauseQuartzRequestDto): PauseQuartzResponseDto {
        val jobKey = JobKey.jobKey(jobName)
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw BadRequestException("quartz.job.NotFound")
            }

            scheduler.pauseJob(jobKey)
        } catch (e: SchedulerException) {
            logger.error("pauseJob error!!", e)
            throw NotExpectResultException("quartz.job.SchedulerException = {${e}}")
        }

        val jobScheduler = jobSchedulerRepository.findByJobName(jobName)
            .orElseThrow { ResourceNotFoundException("job scheduler does not exist") }

        if (jobScheduler.isDeleted) throw BadRequestException("job scheduler already deleted")

        jobScheduler.pause = GlobalStatus.SCHEDULER_STATUS_PAUSE
        jobSchedulerRepository.save(jobScheduler)

        return PauseQuartzResponseDto(true)
    }

    override fun resumeJob(jobName: String, resumeQuartzRequestDto: ResumeQuartzRequestDto): ResumeQuartzResponseDto {
        val jobKey = JobKey.jobKey(jobName)
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw BadRequestException("quartz.job.NotFound")
            }

            scheduler.resumeJob(jobKey)
        } catch (e: SchedulerException) {
            logger.error("resumeJob error!!", e)
            throw NotExpectResultException("quartz.job.SchedulerException = {${e}}")
        }

        val jobScheduler = jobSchedulerRepository.findByJobName(jobName)
            .orElseThrow { ResourceNotFoundException("job scheduler does not exist") }

        if (jobScheduler.isDeleted) throw BadRequestException("job scheduler already deleted")

        jobScheduler.pause = GlobalStatus.SCHEDULER_STATUS_RUNNING
        jobSchedulerRepository.save(jobScheduler)

        return ResumeQuartzResponseDto(true)
    }

    private fun addJob(jobName: String, cron: String) {
        try {
            val jobDetail: JobDetail = createJobDetail(jobName)
            val cronTrigger: CronTrigger = createCronTrigger(jobName, cron)
            scheduler.scheduleJob(jobDetail, cronTrigger)
        } catch (e: SchedulerException) {
            logger.error("addJob error!!", e)
            throw NotExpectResultException("quartz.job.add.Exception = {${e}}")
        }
    }

    private fun createJobDetail(jobName: String): JobDetail {
        val jobDataMap = JobDataMap()
        jobDataMap["jobName"] = jobName
        jobDataMap["jobLauncher"] = jobLauncher
        jobDataMap["jobLocator"] = jobLocator

        return JobBuilder.newJob(QuartzJob::class.java)
            .withIdentity(jobName)
            .setJobData(jobDataMap)
            .storeDurably()
            .build()
    }

    private fun createCronTrigger(jobNm: String, cron: String): CronTrigger = TriggerBuilder.newTrigger()
        .withIdentity(JobKey(jobNm).name)
        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
        .build()

    private fun isValidCronExpression(cronExpression: String): Boolean {
        try {
            CronExpression(cronExpression)
            return true
        } catch (e: Exception) {
            logger.error("cron is not valid - cron = {}", cronExpression)
            return false
        }
    }
}