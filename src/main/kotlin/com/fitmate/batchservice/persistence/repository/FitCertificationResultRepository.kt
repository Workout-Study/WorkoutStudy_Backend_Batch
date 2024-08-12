package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.CertificationStatus
import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.*

interface FitCertificationResultRepository : JpaRepository<FitCertificationResult, Long> {
    fun findByFitCertificationId(fitCertificationId: Long): Optional<FitCertificationResult>
    fun findByFitCertificationIdAndState(
        fitCertificationId: Long,
        state: Boolean
    ): Optional<FitCertificationResult>

    @Query(
        "SELECT result " +
                "FROM fit_certification_result AS result " +
                "WHERE result.fitGroupId = :fitGroupId " +
                "AND result.userId = :userId " +
                "AND result.createdAt >= :startDate " +
                "AND result.createdAt <= :endDate " +
                "AND result.certificationStatus = :certificationStatus"
    )
    fun findLastWeekCertificationsByUserAndFitGroupAndStatus(
        @Param("fitGroupId") fitGroupId: Long,
        @Param("userId") userId: Int,
        @Param("startDate") startDate: Instant,
        @Param("endDate") endDate: Instant,
        @Param("certificationStatus") certificationStatus: CertificationStatus
    ): List<FitCertificationResult>
}