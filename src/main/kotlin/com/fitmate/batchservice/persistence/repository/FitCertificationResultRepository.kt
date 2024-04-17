package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitCertificationResultRepository : JpaRepository<FitCertificationResult, Long> {
    fun findByFitCertificationId(fitCertificationId: Long): Optional<FitCertificationResult>
    fun findByFitCertificationIdAndState(
        fitCertificationId: Long,
        state: Boolean
    ): Optional<FitCertificationResult>
}