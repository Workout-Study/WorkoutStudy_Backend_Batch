package com.fitmate.batchservice.dto.quartz

import jakarta.validation.constraints.NotEmpty

data class RegisterQuartzRequestDto(
    @field:NotEmpty val jobName: String,
    @field:NotEmpty val cron: String,
    @field:NotEmpty val requestUserId: String
)
