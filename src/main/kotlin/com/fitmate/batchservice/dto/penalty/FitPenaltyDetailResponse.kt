package com.fitmate.batchservice.dto.penalty

import java.time.Instant

data class FitPenaltyDetailResponse(
    val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: String,
    val amount: Int,
    val createdAt: Instant
)
