package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.dto.fit.FitMateResponseDto
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity(name = "fit_mate_for_read")
@EqualsAndHashCode
class FitMateForRead private constructor(
    val fitGroupId: Long,
    @Column(unique = true) val fitMateId: Long,
    val fitMateUserId: Int,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun createByDetail(
            fitGroupId: Long,
            dto: FitMateResponseDto,
            eventPublisher: String
        ): FitMateForRead =
            FitMateForRead(
                fitGroupId,
                dto.fitMateId,
                dto.fitMateUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED,
                eventPublisher
            )
    }
}
