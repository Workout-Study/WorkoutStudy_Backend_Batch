package com.fitmate.batchservice.dto.fit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitGroupResponseDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    val penaltyAmount: Int,
    val state: Boolean
)
