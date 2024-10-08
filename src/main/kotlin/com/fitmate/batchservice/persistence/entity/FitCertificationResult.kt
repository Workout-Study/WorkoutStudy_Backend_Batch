package com.fitmate.batchservice.persistence.entity

import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity(name = "fit_certification_result")
@Table(indexes = [Index(columnList = "fitCertificationId")])
@EqualsAndHashCode
class FitCertificationResult(
    var fitCertificationId: Long,
    var fitGroupId: Long,
    var userId: Int,
    @Enumerated(EnumType.STRING)
    var certificationStatus: CertificationStatus,
    state: Boolean,
    createUser: String
) : BaseEntity(state, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}