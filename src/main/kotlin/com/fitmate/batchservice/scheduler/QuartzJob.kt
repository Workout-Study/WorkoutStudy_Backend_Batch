package com.fitmate.batchservice.scheduler

import org.quartz.JobExecutionContext
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.quartz.QuartzJobBean

class QuartzJob(
    private val jobName: String,
    private val jobLauncher: JobLauncher,
    private val jobLocator: JobLocator
) : QuartzJobBean() {

    override fun executeInternal(context: JobExecutionContext) {
        try {
            val job = jobLocator.getJob(jobName)
            val params = JobParametersBuilder()
                .addString("JobID", System.currentTimeMillis().toString())
                .toJobParameters()

            jobLauncher.run(job, params)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}