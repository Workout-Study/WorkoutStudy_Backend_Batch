package com.fitmate.batchservice.job

import com.fitmate.batchservice.persistence.entity.FitMateForRead
import com.fitmate.batchservice.persistence.entity.FitPenalty
import com.fitmate.batchservice.service.FitPenaltyJobService
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FitPenaltyIssueJobConfig(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val fitPenaltyJobService: FitPenaltyJobService
) {

    companion object {
        const val CHUNK_SIZE = 10
    }

    @Bean
    fun fitPenaltyIssueJob(jobRepository: JobRepository): Job =
        JobBuilder(JobNames.FIT_PENALTY_ISSUE_JOB.jobName, jobRepository)
            .start(fitPenaltyFirstStep(jobRepository))
            .build()

    @Bean
    fun fitPenaltyFirstStep(jobRepository: JobRepository): Step =
        StepBuilder(JobNames.FIT_PENALTY_ISSUE_JOB.jobName + "FirstStep", jobRepository)
            .chunk<FitMateForRead, FitPenalty>(CHUNK_SIZE, transactionManager)
            .reader(fitPenaltyItemReader())
            .processor(fitPenaltyItemProcessor())
            .writer(fitPenaltyItemWriter())
            .build()

    @Bean
    @StepScope
    fun fitPenaltyItemReader(): JpaPagingItemReader<FitMateForRead> {
        return JpaPagingItemReaderBuilder<FitMateForRead>()
            .name(JobNames.FIT_PENALTY_ISSUE_JOB.jobName.plus("_READER"))
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(
                "SELECT mate FROM fit_mate_for_read AS mate " +
                        "LEFT JOIN fit_group_for_read AS group " +
                        "ON mate.fitGroupId = group.fitGroupId " +
                        "AND group.state = false " +
                        "WHERE mate.state = false " +
                        "AND group.state IS NOT NULL " +
                        "ORDER BY mate.fitMateId DESC"
            )
            .build()
    }

    @Bean
    @StepScope
    fun fitPenaltyItemProcessor(): ItemProcessor<FitMateForRead, FitPenalty?> {
        return ItemProcessor<FitMateForRead, FitPenalty?> { item ->
            fitPenaltyJobService.checkCertificationAndIssueFitPenalty(item)
        }
    }

    @Bean
    @StepScope
    fun fitPenaltyItemWriter(): ItemWriter<FitPenalty> {
        return ItemWriter<FitPenalty> { result ->
            result.forEach {
                fitPenaltyJobService.saveFitPenalty(it)
            }
        }
    }
}