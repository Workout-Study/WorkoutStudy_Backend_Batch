package com.fitmate.batchservice.scheduler

import org.quartz.JobExecutionContext
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.quartz.QuartzJobBean

class QuartzJob : QuartzJobBean() {

    override fun executeInternal(context: JobExecutionContext) {
        try {
            val jobName = context.mergedJobDataMap["jobName"] as String
            val jobLauncher = context.mergedJobDataMap["jobLauncher"] as JobLauncher
            val jobLocator = context.mergedJobDataMap["jobLocator"] as JobLocator

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