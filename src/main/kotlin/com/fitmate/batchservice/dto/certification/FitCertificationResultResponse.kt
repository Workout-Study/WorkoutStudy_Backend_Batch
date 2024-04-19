package com.fitmate.batchservice.dto.certification

import com.fitmate.batchservice.persistence.entity.CertificationStatus
import java.time.Instant

data class FitCertificationResultResponse(
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val createdAt: Instant
)