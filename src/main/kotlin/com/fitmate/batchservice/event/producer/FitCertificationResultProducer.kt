package com.fitmate.batchservice.event.producer

interface FitCertificationResultProducer {
    fun produceRegisterFitCertificationResult(fitCertificationId: Long)
}