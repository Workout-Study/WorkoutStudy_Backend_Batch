package com.fitmate.batchservice.job

import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import com.fitmate.batchservice.service.CertificationResultJobService
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class CertificationResultJobConfig(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val certificationResultJobService: CertificationResultJobService
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
    fun itemReader(): JpaPagingItemReader<FitCertificationForRead> {
        return JpaPagingItemReaderBuilder<FitCertificationForRead>()
            .name(JobNames.CERTIFICATION_RESULT_JOB.jobName.plus("_READER"))
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(
                "SELECT read FROM fit_certification_for_read AS read " +
                        "LEFT JOIN fit_certification_result AS result " +
                        "WHERE read.state = 0 " +
                        "AND read.certification_status = 'REQUESTED' " +
                        "AND result.id IS NULL" +
                        "ORDER BY read.id DESC"
            )
            .build()
    }

    @Bean
    @StepScope
    fun itemProcessor(): ItemProcessor<FitCertificationForRead, FitCertificationResult> {
        return ItemProcessor<FitCertificationForRead, FitCertificationResult> { item ->
            certificationResultJobService.getCertificationResult(item)
        }
    }

    @Bean
    @StepScope
    fun itemWriter(): JpaItemWriter<FitCertificationResult> {
        return JpaItemWriterBuilder<FitCertificationResult>()
            .usePersist(true)
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}