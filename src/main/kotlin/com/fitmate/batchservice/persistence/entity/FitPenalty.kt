package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.myfit.adapter.out.persistence.converter.BooleanNumberConverter
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitPenalty(
    val userId: Int,
    val fitGroupId: Long,
    val amount: Int,
    @Convert(converter = BooleanNumberConverter::class)
    var isConsumed: Boolean,
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), "BATCH") {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}