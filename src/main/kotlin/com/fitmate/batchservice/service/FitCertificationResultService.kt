package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.certification.FitCertificationResultResponse

interface FitCertificationResultService {
    fun getFitCertificationResult(fitCertificationId: Long): FitCertificationResultResponse
}