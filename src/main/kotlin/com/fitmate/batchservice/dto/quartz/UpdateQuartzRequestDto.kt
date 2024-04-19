package com.fitmate.batchservice.dto.quartz

import jakarta.validation.constraints.NotEmpty

data class UpdateQuartzRequestDto(
    @field:NotEmpty val cron: String,
    @field:NotEmpty val requestUserId: String
)
