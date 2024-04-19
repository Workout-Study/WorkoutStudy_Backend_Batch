package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.quartz.*
import com.fitmate.batchservice.scheduler.QuartzListener

interface QuartzService {

    fun register()

    fun start()

    fun addListener(jobListener: QuartzListener)

    fun shutdown()

    fun clear()

    fun registerJob(registerQuartzRequestDto: RegisterQuartzRequestDto): RegisterQuartzResponseDto
    fun updateJob(jobName: String, updateQuartzRequestDto: UpdateQuartzRequestDto): UpdateQuartzResponseDto
    fun deleteJob(jobName: String, deleteQuartzRequestDto: DeleteQuartzRequestDto): DeleteQuartzResponseDto
    fun pauseJob(jobName: String, pauseQuartzRequestDto: PauseQuartzRequestDto): PauseQuartzResponseDto
    fun resumeJob(jobName: String, resumeQuartzRequestDto: ResumeQuartzRequestDto): ResumeQuartzResponseDto
}