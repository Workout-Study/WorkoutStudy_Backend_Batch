package com.fitmate.batchservice.dto.certification

data class FitCertificationDetailProgressResponseDto(
    val fitCertificationId: Long,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int
)
