package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.uris.FitGroupUri
import com.fitmate.batchservice.dto.fit.FitGroupResponseDto
import com.fitmate.batchservice.dto.fit.SaveFitGroupForReadResponseDto
import com.fitmate.batchservice.exception.BadRequestException
import com.fitmate.batchservice.exception.NotExpectResultException
import com.fitmate.batchservice.persistence.entity.FitGroupForRead
import com.fitmate.batchservice.persistence.repository.FitGroupForReadRepository
import com.fitmate.batchservice.utils.SenderUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupForReadServiceImpl(
    private val senderUtils: SenderUtils,
    private val fitGroupForReadRepository: FitGroupForReadRepository
) : FitGroupForReadService {

    @Transactional
    override fun saveFitGroupForRead(fitGroupId: String, eventPublisher: String): SaveFitGroupForReadResponseDto {
        val fitGroupIdLong = convertFitGroupId(fitGroupId)

        val fitGroupDetail = getFitGroupDetail(fitGroupIdLong)

        val fitGroupForRead = fitGroupForReadRepository.findByFitGroupId(fitGroupIdLong)
            .orElse(FitGroupForRead.createByFitGroupDetail(fitGroupDetail, eventPublisher))

        fitGroupForRead.updateByFitGroupDetail(fitGroupDetail, eventPublisher)
        val savedFitGroupForRead = fitGroupForReadRepository.save(fitGroupForRead)

        return SaveFitGroupForReadResponseDto(savedFitGroupForRead.id != null)
    }

    private fun convertFitGroupId(fitGroupIdString: String): Long {
        try {
            return fitGroupIdString.toLong()
        } catch (exception: Exception) {
            throw BadRequestException("fit group must be long")
        }
    }

    private fun getFitGroupDetail(fitGroupId: Long): FitGroupResponseDto {
        val uriEndPoint = "${FitGroupUri.GROUP_ROOT}/${fitGroupId}"

        val response: ResponseEntity<FitGroupResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitGroupResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit group response body is null")
    }
}