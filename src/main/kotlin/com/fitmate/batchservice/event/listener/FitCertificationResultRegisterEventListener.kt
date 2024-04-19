package com.fitmate.batchservice.event.listener

import com.fitmate.batchservice.event.event.RegisterFitCertificationResultEvent
import com.fitmate.batchservice.event.producer.FitCertificationResultProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitCertificationResultRegisterEventListener(
    private val fitCertificationResultProducer: FitCertificationResultProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationResultRegisterEventListener::class.java)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceRegisterFitCertificationResult(registerFitCertificationResultEvent: RegisterFitCertificationResultEvent) {
        logger?.info(
            "RegisterFitCertificationResultEvent with produceRegisterFitCertificationResult start - fit certification id = {}",
            registerFitCertificationResultEvent.fitCertificationId
        )
        fitCertificationResultProducer.produceRegisterFitCertificationResult(registerFitCertificationResultEvent.fitCertificationId)
    }
}