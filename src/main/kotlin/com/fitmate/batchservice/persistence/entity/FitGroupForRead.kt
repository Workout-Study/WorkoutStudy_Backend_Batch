package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.dto.fit.FitGroupResponseDto
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity(name = "fit_group_for_read")
@EqualsAndHashCode
class FitGroupForRead private constructor(
    @Column(unique = true) val fitGroupId: Long,
    var fitGroupName: String,
    var cycle: Int,
    var frequency: Int,
    var penaltyAmount: Int,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateByFitGroupDetail(dto: FitGroupResponseDto, eventPublisher: String) {
        this.fitGroupName = dto.fitGroupName
        this.cycle = dto.cycle
        this.frequency = dto.frequency
        this.penaltyAmount = dto.penaltyAmount
        this.state = dto.state
        this.updatedAt = Instant.now()
        this.updateUser = eventPublisher
    }

    companion object {
        fun createByFitGroupDetail(
            fitGroupDetail: FitGroupResponseDto,
            eventPublisher: String
        ): FitGroupForRead =
            FitGroupForRead(
                fitGroupDetail.fitGroupId,
                fitGroupDetail.fitGroupName,
                fitGroupDetail.cycle,
                fitGroupDetail.frequency,
                fitGroupDetail.penaltyAmount,
                fitGroupDetail.state,
                eventPublisher
            )
    }
}
