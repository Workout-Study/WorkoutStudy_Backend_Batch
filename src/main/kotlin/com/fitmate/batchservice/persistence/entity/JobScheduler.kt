package com.fitmate.batchservice.persistence.entity

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.dto.quartz.RegisterQuartzRequestDto
import com.fitmate.batchservice.dto.quartz.UpdateQuartzRequestDto
import com.fitmate.myfit.adapter.out.persistence.converter.BooleanNumberConverter
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class JobScheduler(
    @Column(unique = true) val jobName: String,
    var cron: String,
    @Convert(converter = BooleanNumberConverter::class)
    var pause: Boolean = GlobalStatus.SCHEDULER_STATUS_RUNNING,
    state: Boolean = GlobalStatus.PERSISTENCE_NOT_DELETED,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateByRegisterDto(registerQuartzRequestDto: RegisterQuartzRequestDto) {
        cron = registerQuartzRequestDto.cron
        state = GlobalStatus.PERSISTENCE_NOT_DELETED
        pause = GlobalStatus.SCHEDULER_STATUS_RUNNING
        updatedAt = Instant.now()
        updateUser = registerQuartzRequestDto.requestUserId
    }

    fun updateByUpdateDto(updateQuartzRequestDto: UpdateQuartzRequestDto) {
        cron = updateQuartzRequestDto.cron
        state = GlobalStatus.PERSISTENCE_NOT_DELETED
        updatedAt = Instant.now()
        updateUser = updateQuartzRequestDto.requestUserId
    }
}