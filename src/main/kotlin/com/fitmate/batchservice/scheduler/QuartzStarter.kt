package com.fitmate.batchservice.scheduler

import com.fitmate.batchservice.service.QuartzService
import jakarta.annotation.PreDestroy

class QuartzStarter(private val quartzService: QuartzService) {

    init {
        quartzService.clear()
        quartzService.addListener(QuartzListener())
        quartzService.register()
        quartzService.start()
    }

    @PreDestroy
    fun destroy() {
        quartzService.shutdown()
    }
}