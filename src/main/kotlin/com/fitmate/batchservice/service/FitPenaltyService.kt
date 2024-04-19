package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.penalty.FitPenaltyDetailResponse

interface FitPenaltyService {
    fun getFitPenalty(fitPenaltyId: Long): FitPenaltyDetailResponse
}