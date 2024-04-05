package com.fitmate.batchservice.persistence.entity

import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitMateForRead private constructor(
    val fitGroupId: Long,
    @Column(unique = true) val fitMateId: Long,
    val fitMateUserId: String,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {

    }
}
