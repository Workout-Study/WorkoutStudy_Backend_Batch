package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.common.uris.MyFitUri
import com.fitmate.batchservice.dto.certification.FitCertificationDetailProgressResponseDto
import com.fitmate.batchservice.event.event.RegisterFitCertificationResultEvent
import com.fitmate.batchservice.exception.NotExpectResultException
import com.fitmate.batchservice.persistence.entity.CertificationStatus
import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import com.fitmate.batchservice.persistence.entity.FitCertificationResult
import com.fitmate.batchservice.persistence.repository.FitCertificationResultRepository
import com.fitmate.batchservice.utils.SenderUtils
import org.springframework.batch.item.Chunk
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CertificationResultJobServiceImpl(
    private val senderUtils: SenderUtils,
    private val fitCertificationResultRepository: FitCertificationResultRepository,
    private val eventPublisher: ApplicationEventPublisher
) : CertificationResultJobService {

    override fun getCertificationResult(fitCertificationForRead: FitCertificationForRead): FitCertificationResult {
        val uriEndPoint = "${MyFitUri.FIT_CERTIFICATION_PROGRESS}/${fitCertificationForRead.fitCertificationId}"

        val response: ResponseEntity<FitCertificationDetailProgressResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitCertificationDetailProgressResponseDto>() {
                })

        val fitCertificationProgress =
            response.body ?: throw NotExpectResultException("fit certification progress body is empty")

        val fitCertificationStatusResult =
            if (fitCertificationProgress.agreeCount > fitCertificationProgress.disagreeCount) CertificationStatus.CERTIFIED
            else CertificationStatus.REJECTED

        return FitCertificationResult(
            fitCertificationProgress.fitCertificationId,
            fitCertificationStatusResult,
            GlobalStatus.PERSISTENCE_NOT_DELETED,
            "BATCH"
        )
    }

    @Transactional
    override fun saveFitCertificationResult(result: Chunk<out FitCertificationResult>) {
        result.forEach {
            fitCertificationResultRepository.save(it)
            eventPublisher.publishEvent(RegisterFitCertificationResultEvent(it.fitCertificationId))
        }
    }
}