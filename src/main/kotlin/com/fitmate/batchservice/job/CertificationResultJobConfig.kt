package com.fitmate.batchservice.job

import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class CertificationResultJobConfig(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val dataSource: DataSource
) {

    companion object {
        const val CHUNK_SIZE = 100
    }

    @Bean
    fun certificationResultJob(jobRepository: JobRepository): Job =
        JobBuilder(JobNames.CERTIFICATION_RESULT_JOB.jobName, jobRepository)
            .start(certificationFirstStep(jobRepository))
            .build()

    @Bean
    fun certificationFirstStep(jobRepository: JobRepository): Step =
        StepBuilder(JobNames.CERTIFICATION_RESULT_JOB.jobName + "FirstStep", jobRepository)
            .chunk<FitCertificationForRead, FitCertificationResult>(CHUNK_SIZE, transactionManager)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build()

    @Bean
    @StepScope
    fun itemReader(): JdbcCursorItemReader<FitCertificationForRead> {
        return JdbcCursorItemReaderBuilder<FitCertificationForRead>()
            .name(JobNames.CERTIFICATION_RESULT_JOB.jobName.plus("_READER"))
            .sql("SELECT * FROM fit_certification_for_read WHERE state = 0 AND certification_status = 'REQUESTED'")
            .rowMapper(BeanPropertyRowMapper(FitCertificationForRead::class.java))
            .fetchSize(CHUNK_SIZE)
            .dataSource(dataSource)
            .build()
    }

    @Bean
    @StepScope
    fun itemProcessor(): ItemProcessor<FitCertificationForRead, FitCertificationResult> {
        return ItemProcessor<FitCertificationForRead, FitCertificationResult> { item ->
            FitCertificationResult(item.fitCertificationId, item.certificationStatus, item.state, "BATCH")
        }
    }

    @Bean
    @StepScope
    fun itemWriter(): ItemWriter<FitCertificationResult> {
        return ItemWriter<FitCertificationResult> { }
    }
}