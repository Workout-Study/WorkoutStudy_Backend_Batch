package com.fitmate.batchservice.event.producer

interface FitPenaltyProducer {
    fun produceRegisterFitPenalty(fitPenaltyId: Long)
}