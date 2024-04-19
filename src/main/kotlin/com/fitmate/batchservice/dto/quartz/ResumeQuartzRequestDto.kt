package com.fitmate.batchservice.dto.quartz

import jakarta.validation.constraints.NotEmpty

data class ResumeQuartzRequestDto(@field:NotEmpty val requestUserId: String)
