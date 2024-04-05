package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitGroupForRead
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitGroupForReadRepository : JpaRepository<FitGroupForRead, Long> {
    fun findByFitGroupId(fitGroupId: Long): Optional<FitGroupForRead>
}