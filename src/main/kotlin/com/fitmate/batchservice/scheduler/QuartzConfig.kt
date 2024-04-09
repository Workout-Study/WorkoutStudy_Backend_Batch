package com.fitmate.batchservice.scheduler

import com.fitmate.batchservice.service.QuartzService
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor
import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.io.IOException
import java.util.*

@Configuration
class QuartzConfig(
    private val quartzService: QuartzService
) {

    @Bean
    fun jobRegistryBeanPostProcessor(jobRegistry: JobRegistry): JobRegistryBeanPostProcessor {
        val jobRegistryBeanPostProcessor = JobRegistryBeanPostProcessor()
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry)
        return jobRegistryBeanPostProcessor
    }

    @Bean
    @Throws(IOException::class)
    fun schedulerFactoryBean(): SchedulerFactoryBean {
        val scheduler = SchedulerFactoryBean()
        scheduler.setQuartzProperties(quartzProperties()!!)
        return scheduler
    }

    @Bean
    @Throws(IOException::class)
    fun quartzProperties(): Properties? {
        val propertiesFactoryBean = PropertiesFactoryBean()
        propertiesFactoryBean.setLocation(ClassPathResource("/quartz.properties"))
        propertiesFactoryBean.afterPropertiesSet()

        return propertiesFactoryBean.getObject()
    }

    @Bean
    fun quartzStarter(): QuartzStarter = QuartzStarter(quartzService)
}