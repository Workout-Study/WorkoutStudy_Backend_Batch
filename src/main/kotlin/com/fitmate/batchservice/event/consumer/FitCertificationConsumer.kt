package com.fitmate.batchservice.event.consumer

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.service.FitCertificationForReadService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitCertificationConsumer(private val fitCertificationForReadService: FitCertificationForReadService) {

    /**
     * kafka fit certification event listener inbound
     *
     * @param fitCertificationId fit certification id where an event occurred
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_CERTIFICATION], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitCertificationListener(fitCertificationId: String) {
        FitGroupEventConsumer.logger?.info(
            "KafkaListener FitCertificationEvent with fitCertificationListener start - fit certification id = {}",
            fitCertificationId
        )
        fitCertificationForReadService.saveFitCertificationForRead(fitCertificationId, "kafka")
    }
}