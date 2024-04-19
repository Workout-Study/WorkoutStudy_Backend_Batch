package com.fitmate.batchservice.service

import com.fitmate.batchservice.persistence.entity.FitMateForRead
import com.fitmate.batchservice.persistence.entity.FitPenalty

interface FitPenaltyJobService {
    fun checkCertificationAndIssueFitPenalty(fitMateForRead: FitMateForRead): FitPenalty?
    fun saveFitPenalty(fitPenalty: FitPenalty)
}