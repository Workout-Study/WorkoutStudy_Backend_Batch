package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.dto.penalty.FitPenaltyDetailResponse
import com.fitmate.batchservice.exception.ResourceNotFoundException
import com.fitmate.batchservice.persistence.repository.FitPenaltyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitPenaltyServiceImpl(
    private val fitPenaltyRepository: FitPenaltyRepository
) : FitPenaltyService {

    @Transactional
    override fun getFitPenalty(fitPenaltyId: Long): FitPenaltyDetailResponse {
        val fitPenalty = fitPenaltyRepository.findByIdAndState(fitPenaltyId, GlobalStatus.PERSISTENCE_NOT_DELETED)
            .orElseThrow { ResourceNotFoundException("fit penalty does not exist") }

        fitPenalty.isConsumed = true

        return FitPenaltyDetailResponse(
            fitPenalty.id!!,
            fitPenalty.fitGroupId,
            fitPenalty.userId,
            fitPenalty.amount,
            fitPenalty.createdAt
        )
    }
}