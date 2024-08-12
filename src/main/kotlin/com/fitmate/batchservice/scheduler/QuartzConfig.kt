package com.fitmate.batchservice.scheduler

import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.io.IOException
import java.util.*

@Configuration
class QuartzConfig(
    private val beanFactory: AutowireCapableBeanFactory
) {

    @Bean
    fun jobFactory(): AutowiringSpringBeanJobFactory {
        return AutowiringSpringBeanJobFactory(beanFactory)
    }

    @Bean
    @Throws(IOException::class)
    fun schedulerFactoryBean(jobFactory: AutowiringSpringBeanJobFactory): SchedulerFactoryBean {
        val scheduler = SchedulerFactoryBean()
        scheduler.setQuartzProperties(quartzProperties()!!)
        scheduler.setJobFactory(jobFactory)
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
}