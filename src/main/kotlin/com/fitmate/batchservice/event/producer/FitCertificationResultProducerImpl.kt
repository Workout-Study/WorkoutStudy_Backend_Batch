package com.fitmate.batchservice.event.producer

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.persistence.repository.FitCertificationResultRepository
import org.apache.kafka.common.errors.ResourceNotFoundException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class FitCertificationResultProducerImpl(
    private val fitCertificationResultRepository: FitCertificationResultRepository,
    private val fitCertificationResultKafkaTemplate: KafkaTemplate<String, String>
) : FitCertificationResultProducer {

    override fun produceRegisterFitCertificationResult(fitCertificationId: Long) {
        fitCertificationResultRepository.findByFitCertificationId(fitCertificationId)
            .orElseThrow { ResourceNotFoundException("fit certification result does not found") }

        fitCertificationResultKafkaTemplate.send(
            GlobalStatus.KAFKA_TOPIC_FIT_CERTIFICATION_RESULT,
            fitCertificationId.toString()
        )
    }
}