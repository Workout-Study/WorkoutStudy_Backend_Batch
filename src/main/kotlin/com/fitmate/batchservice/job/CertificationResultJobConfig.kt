package com.fitmate.batchservice.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class CertificationResultJobConfig(
    private val transactionManager: PlatformTransactionManager
) {

    @Bean
    fun voteResultJob(jobRepository: JobRepository): Job =
        JobBuilder(JobNames.CERTIFICATION_RESULT_JOB.jobName, jobRepository)
            .start(voteFirstStep(jobRepository))
            .build()

    @Bean
    fun voteFirstStep(jobRepository: JobRepository): Step =
        StepBuilder(JobNames.CERTIFICATION_RESULT_JOB.jobName + "FirstStep", jobRepository)
            .chunk<String, String>(100, transactionManager)
//            .reader()
//            .writer()
            .build()
}