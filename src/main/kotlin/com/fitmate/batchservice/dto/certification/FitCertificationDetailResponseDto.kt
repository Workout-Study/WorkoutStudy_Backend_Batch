package com.fitmate.batchservice.dto.certification

import com.fitmate.batchservice.persistence.entity.CertificationStatus
import com.fitmate.batchservice.utils.DateParseUtils
import java.time.Instant

data class FitCertificationDetailResponseDto(
    val fitGroupId: Long,
    val fitCertificationId: Long,
    val userId: Int,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    val createdAt: String,
    val voteEndDate: String
) {
    val voteEndDateInstant: Instant = DateParseUtils.stringToInstant(voteEndDate)
}
