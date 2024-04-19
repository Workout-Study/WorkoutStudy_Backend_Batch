package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitCertificationForReadRepository : JpaRepository<FitCertificationForRead, Long> {
    fun findByFitCertificationId(fitCertificationId: Long): Optional<FitCertificationForRead>
}