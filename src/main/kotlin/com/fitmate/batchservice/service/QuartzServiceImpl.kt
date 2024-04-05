package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.quartz.*
import org.springframework.stereotype.Service

@Service
class QuartzServiceImpl : QuartzService {
    override fun registerJob(registerQuartzRequestDto: RegisterQuartzRequestDto): RegisterQuartzResponseDto {
        TODO("Not yet implemented")
    }

    override fun updateJob(jobName: String, updateQuartzRequestDto: UpdateQuartzRequestDto): UpdateQuartzResponseDto {
        TODO("Not yet implemented")
    }

    override fun deleteJob(jobName: String, deleteQuartzRequestDto: DeleteQuartzRequestDto): DeleteQuartzResponseDto {
        TODO("Not yet implemented")
    }

    override fun pauseJob(jobName: String, pauseQuartzRequestDto: PauseQuartzRequestDto): PauseQuartzResponseDto {
        TODO("Not yet implemented")
    }

    override fun resumeJob(jobName: String, resumeQuartzRequestDto: ResumeQuartzRequestDto): ResumeQuartzResponseDto {
        TODO("Not yet implemented")
    }
}