package com.fitmate.batchservice.service

import com.fitmate.batchservice.dto.fit.SaveFitGroupForReadResponseDto

interface FitGroupForReadService {
    fun saveFitGroupForRead(fitGroupId: String, eventPublisher: String): SaveFitGroupForReadResponseDto
}