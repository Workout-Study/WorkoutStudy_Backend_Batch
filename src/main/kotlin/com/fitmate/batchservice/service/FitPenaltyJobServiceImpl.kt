package com.fitmate.batchservice.service

import com.fitmate.batchservice.persistence.entity.FitMateForRead
import com.fitmate.batchservice.persistence.entity.FitPenalty
import com.fitmate.batchservice.persistence.repository.FitCertificationResultRepository
import com.fitmate.batchservice.persistence.repository.FitGroupForReadRepository
import com.fitmate.batchservice.persistence.repository.FitPenaltyRepository
import org.springframework.stereotype.Service

@Service
class FitPenaltyJobServiceImpl(
    private val fitGroupForReadRepository: FitGroupForReadRepository,
    private val fitPenaltyRepository: FitPenaltyRepository,
    private val fitCertificationResultRepository: FitCertificationResultRepository
) : FitPenaltyJobService {

    override fun checkCertificationAndIssueFitPenalty(fitMateForRead: FitMateForRead): FitPenalty? {
        TODO("Not yet implemented")
    }
}