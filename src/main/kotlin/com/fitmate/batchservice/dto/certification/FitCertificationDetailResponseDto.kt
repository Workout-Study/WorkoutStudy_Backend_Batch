package com.fitmate.batchservice.dto.certification

import com.fitmate.batchservice.persistence.entity.CertificationStatus
import java.time.Instant

data class FitCertificationDetailResponseDto(
    val fitGroupId: Long,
    val fitCertificationId: Long,
    val userId: Int,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    val createdAt: Instant,
    val voteEndDate: Instant
)
