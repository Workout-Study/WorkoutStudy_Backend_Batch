package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.dto.certification.FitCertificationDetailResponseDto
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@Table(indexes = [Index(columnList = "fitCertificationId")])
@EqualsAndHashCode
class FitCertificationForRead private constructor(
    var fitCertificationId: Long,
    var fitGroupId: Long,
    var userId: String,
    var certificationEndDate: Instant,
    @Enumerated(EnumType.STRING)
    var certificationStatus: CertificationStatus,
    state: Boolean,
    createUser: String
) : BaseEntity(state, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateByFitCertificationDetail(
        fitCertificationDetail: FitCertificationDetailResponseDto,
        eventPublisher: String
    ) {
        this.fitCertificationId = fitCertificationDetail.fitCertificationId
        this.certificationEndDate = fitCertificationDetail.voteEndDate
        this.certificationStatus = fitCertificationDetail.certificationStatus
        this.fitGroupId = fitCertificationDetail.fitGroupId
        this.userId = fitCertificationDetail.userId
        this.state = fitCertificationDetail.state
        this.updatedAt = Instant.now()
        this.updateUser = eventPublisher
    }

    companion object {
        fun createByFitCertificationDetail(
            fitCertificationDetail: FitCertificationDetailResponseDto,
            eventPublisher: String
        ): FitCertificationForRead =
            FitCertificationForRead(
                fitCertificationDetail.fitCertificationId,
                fitCertificationDetail.fitGroupId,
                fitCertificationDetail.userId,
                fitCertificationDetail.voteEndDate,
                fitCertificationDetail.certificationStatus,
                fitCertificationDetail.state,
                eventPublisher
            )
    }
}