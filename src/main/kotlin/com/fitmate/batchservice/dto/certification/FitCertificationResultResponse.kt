package com.fitmate.batchservice.dto.certification

import com.fitmate.batchservice.persistence.entity.CertificationStatus
import com.fitmate.batchservice.utils.DateParseUtils
import java.time.Instant

data class FitCertificationResultResponse(
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    private val createdAtInstant: Instant
) {
    val createdAt: String = DateParseUtils.instantToString(createdAtInstant)
}