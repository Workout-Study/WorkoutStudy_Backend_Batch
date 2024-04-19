package com.fitmate.batchservice.event.listener

import com.fitmate.batchservice.event.event.RegisterFitPenaltyEvent
import com.fitmate.batchservice.event.producer.FitPenaltyProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitPenaltyRegisterEventListener(
    private val fitPenaltyProducer: FitPenaltyProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitPenaltyRegisterEventListener::class.java)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceRegisterFitPenalty(registerFitPenaltyEvent: RegisterFitPenaltyEvent) {
        logger?.info(
            "RegisterFitPenaltyEvent with produceRegisterFitPenalty start - fit penalty id = {}",
            registerFitPenaltyEvent.fitPenaltyId
        )
        fitPenaltyProducer.produceRegisterFitPenalty(registerFitPenaltyEvent.fitPenaltyId)
    }
}