package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.certification.SaveFitCertificationForReadResponseDto

interface FitCertificationForReadService {
    fun saveFitCertificationForRead(
        fitCertificationId: String,
        eventPublisher: String
    ): SaveFitCertificationForReadResponseDto
}