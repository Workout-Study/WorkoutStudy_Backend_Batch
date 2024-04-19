package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.uris.MyFitUri
import com.fitmate.batchservice.dto.certification.FitCertificationDetailResponseDto
import com.fitmate.batchservice.dto.certification.SaveFitCertificationForReadResponseDto
import com.fitmate.batchservice.exception.BadRequestException
import com.fitmate.batchservice.exception.NotExpectResultException
import com.fitmate.batchservice.persistence.entity.FitCertificationForRead
import com.fitmate.batchservice.persistence.repository.FitCertificationForReadRepository
import com.fitmate.batchservice.utils.SenderUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitCertificationForReadServiceImpl(
    private val senderUtils: SenderUtils,
    private val fitCertificationForReadRepository: FitCertificationForReadRepository
) : FitCertificationForReadService {

    @Transactional
    override fun saveFitCertificationForRead(
        fitCertificationId: String,
        eventPublisher: String
    ): SaveFitCertificationForReadResponseDto {
        val fitCertificationIdLong = convertFitCertificationId(fitCertificationId)

        val fitCertificationDetail = getFitCertificationDetail(fitCertificationIdLong)

        val fitCertificationForRead = fitCertificationForReadRepository.findByFitCertificationId(fitCertificationIdLong)
            .orElse(FitCertificationForRead.createByFitCertificationDetail(fitCertificationDetail, eventPublisher))

        fitCertificationForRead.updateByFitCertificationDetail(fitCertificationDetail, eventPublisher)
        val savedFitCertificationForRead = fitCertificationForReadRepository.save(fitCertificationForRead)

        return SaveFitCertificationForReadResponseDto(savedFitCertificationForRead.id != null)
    }

    private fun convertFitCertificationId(fitCertificationIdString: String): Long {
        try {
            return fitCertificationIdString.toLong()
        } catch (exception: Exception) {
            throw BadRequestException("fit certification must be long")
        }
    }

    private fun getFitCertificationDetail(fitCertificationId: Long): FitCertificationDetailResponseDto {
        val uriEndPoint = "${MyFitUri.FIT_CERTIFICATION_ROOT}/${fitCertificationId}"

        val response: ResponseEntity<FitCertificationDetailResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitCertificationDetailResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit certification response body is null")
    }
}