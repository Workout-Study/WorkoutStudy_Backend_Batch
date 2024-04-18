package com.fitmate.batchservice.service

import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import org.springframework.batch.item.Chunk

interface CertificationResultJobService {
    fun getCertificationResult(fitCertificationForRead: FitCertificationForRead): FitCertificationResult

    fun saveFitCertificationResult(result: Chunk<out FitCertificationResult>)
}