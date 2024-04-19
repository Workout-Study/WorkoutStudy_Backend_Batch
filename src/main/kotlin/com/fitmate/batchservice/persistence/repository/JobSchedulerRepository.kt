package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.JobScheduler
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobSchedulerRepository : JpaRepository<JobScheduler, Long> {
    fun findByJobName(jobName: String): Optional<JobScheduler>
}