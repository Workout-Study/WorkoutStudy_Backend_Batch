package com.fitmate.batchservice.event.producer

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.exception.ResourceNotFoundException
import com.fitmate.batchservice.persistence.repository.FitPenaltyRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class FitPenaltyProducerImpl(
    private val fitPenaltyRepository: FitPenaltyRepository,
    private val fitPenaltyKafkaTemplate: KafkaTemplate<String, String>
) : FitPenaltyProducer {
    override fun produceRegisterFitPenalty(fitPenaltyId: Long) {
        fitPenaltyRepository.findById(fitPenaltyId)
            .orElseThrow { ResourceNotFoundException("fit penalty does not exist") }

        fitPenaltyKafkaTemplate.send(GlobalStatus.KAFKA_TOPIC_FIT_PENALTY, fitPenaltyId.toString())
    }
}