package com.fitmate.batchservice.dto.fit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitMateResponseDto(
    val fitMateId: Long,
    val fitMateUserId: Int
)
