package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitPenalty
import org.springframework.data.jpa.repository.JpaRepository

interface FitPenaltyRepository : JpaRepository<FitPenalty, Long> {
}