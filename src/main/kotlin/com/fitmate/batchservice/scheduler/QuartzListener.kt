package com.fitmate.batchservice.scheduler

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class QuartzListener : JobListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(QuartzListener::class.java)
    }

    override fun getName(): String = this.javaClass.name

    override fun jobToBeExecuted(context: JobExecutionContext?) {
        logger.info("jobToBeExecuted !!! context = {}", context)
    }

    override fun jobExecutionVetoed(context: JobExecutionContext?) {
        logger.info("jobExecutionVetoed !!! context = {}", context)
    }

    override fun jobWasExecuted(context: JobExecutionContext?, jobException: JobExecutionException?) {
        logger.info("jobWasExecuted !!! context = {${context}}, exception = {${jobException}}")
    }
}