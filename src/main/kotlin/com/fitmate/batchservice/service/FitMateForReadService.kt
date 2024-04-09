package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.fit.SaveFitMateForReadResponseDto

interface FitMateForReadService {
    fun saveFitMateForRead(fitGroupId: String, eventPublisher: String): SaveFitMateForReadResponseDto
}