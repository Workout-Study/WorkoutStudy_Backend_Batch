package com.fitmate.batchservice.dto.quartz

import jakarta.validation.constraints.NotEmpty

data class PauseQuartzRequestDto(@field:NotEmpty val requestUserId: String)
