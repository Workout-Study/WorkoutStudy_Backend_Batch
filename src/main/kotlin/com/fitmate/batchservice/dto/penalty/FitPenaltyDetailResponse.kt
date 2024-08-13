package com.fitmate.batchservice.dto.penalty

import com.fitmate.batchservice.utils.DateParseUtils
import java.time.Instant

data class FitPenaltyDetailResponse(
    val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: Int,
    val amount: Int,
    private val createdAtInstant: Instant
) {
    val createdAt: String = DateParseUtils.instantToString(createdAtInstant)
}
