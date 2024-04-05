package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.quartz.*

interface QuartzService {
    fun registerJob(registerQuartzRequestDto: RegisterQuartzRequestDto): RegisterQuartzResponseDto
    fun updateJob(jobName: String, updateQuartzRequestDto: UpdateQuartzRequestDto): UpdateQuartzResponseDto
    fun deleteJob(jobName: String, deleteQuartzRequestDto: DeleteQuartzRequestDto): DeleteQuartzResponseDto
    fun pauseJob(jobName: String, pauseQuartzRequestDto: PauseQuartzRequestDto): PauseQuartzResponseDto
    fun resumeJob(jobName: String, resumeQuartzRequestDto: ResumeQuartzRequestDto): ResumeQuartzResponseDto
}