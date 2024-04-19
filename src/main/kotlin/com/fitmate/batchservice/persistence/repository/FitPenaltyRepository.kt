package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitPenalty
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.*

interface FitPenaltyRepository : JpaRepository<FitPenalty, Long> {
    fun findByFitGroupIdAndUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(
        fitGroupId: Long,
        userId: String,
        startDate: Instant,
        endDate: Instant
    ): Optional<FitPenalty>

    fun findByIdAndState(fitPenaltyId: Long, state: Boolean): Optional<FitPenalty>
}