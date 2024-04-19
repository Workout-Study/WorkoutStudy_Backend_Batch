package com.fitmate.batchservice.dto.quartz

import jakarta.validation.constraints.NotEmpty

data class DeleteQuartzRequestDto(@field:NotEmpty val requestUserId: String)
