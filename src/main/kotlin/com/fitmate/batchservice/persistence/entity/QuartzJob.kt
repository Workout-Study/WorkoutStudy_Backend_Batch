package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.common.GlobalStatus
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class QuartzJob private constructor(
    @Column(unique = true) val jobName: String,
    var cron: String,
    createUser: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {

    }
}