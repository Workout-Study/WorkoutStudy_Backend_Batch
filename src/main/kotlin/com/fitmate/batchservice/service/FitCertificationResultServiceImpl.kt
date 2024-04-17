package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.dto.certification.FitCertificationResultResponse
import com.fitmate.batchservice.exception.ResourceNotFoundException
import com.fitmate.batchservice.persistence.repository.FitCertificationResultRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitCertificationResultServiceImpl(
    private val fitCertificationResultRepository: FitCertificationResultRepository
) : FitCertificationResultService {

    @Transactional(readOnly = true)
    override fun getFitCertificationResult(fitCertificationId: Long): FitCertificationResultResponse {
        val fitCertificationResult = fitCertificationResultRepository.findByFitCertificationIdAndState(
            fitCertificationId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).orElseThrow { ResourceNotFoundException("fit certification result does not exist") }

        return FitCertificationResultResponse(
            fitCertificationResult.fitCertificationId,
            fitCertificationResult.certificationStatus,
            fitCertificationResult.createdAt
        )
    }
}