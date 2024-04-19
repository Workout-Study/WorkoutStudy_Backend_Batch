package com.fitmate.batchservice.service

import com.fitmate.batchservice.common.GlobalStatus
import com.fitmate.batchservice.common.uris.FitGroupUri
import com.fitmate.batchservice.dto.fit.FitMateDetailsResponseDto
import com.fitmate.batchservice.dto.fit.SaveFitMateForReadResponseDto
import com.fitmate.batchservice.exception.BadRequestException
import com.fitmate.batchservice.exception.NotExpectResultException
import com.fitmate.batchservice.persistence.entity.FitMateForRead
import com.fitmate.batchservice.persistence.repository.FitMateForReadRepository
import com.fitmate.batchservice.utils.SenderUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitMateForReadServiceImpl(
    private val senderUtils: SenderUtils,
    private val fitMateForReadRepository: FitMateForReadRepository
) : FitMateForReadService {

    @Transactional
    override fun saveFitMateForRead(fitGroupId: String, eventPublisher: String): SaveFitMateForReadResponseDto {
        val fitGroupIdLong = convertFitGroupId(fitGroupId)

        val fitMateDetailsResponse = getFitMateDetails(fitGroupIdLong)
        val fitMateDetailList = fitMateDetailsResponse.fitMateDetails

        val fitMateForReadList =
            fitMateForReadRepository.findByFitGroupIdAndState(fitGroupIdLong, GlobalStatus.PERSISTENCE_NOT_DELETED)

        val fitMateDetailIdSet = fitMateDetailList.map { it.fitMateId }.toSet()
        val fitMateForReadIdSet = fitMateForReadList.map { it.fitMateId }.toSet()

        val newFitMateIds = fitMateDetailIdSet.subtract(fitMateForReadIdSet)
        val newFitMates = fitMateDetailList.filter { it.fitMateId in newFitMateIds }
            .map { FitMateForRead.createByDetail(fitMateDetailsResponse.fitGroupId, it, eventPublisher) }

        newFitMates.forEach { fitMateForReadRepository.save(it) }

        val deletedFitMateIds = fitMateForReadIdSet.subtract(fitMateDetailIdSet)
        val deletedFitMates = fitMateForReadList.filter { it.fitMateId in deletedFitMateIds }
            .map { it.delete(); it }

        deletedFitMates.forEach { fitMateForReadRepository.save(it) }

        return SaveFitMateForReadResponseDto(true)
    }

    private fun convertFitGroupId(fitGroupIdString: String): Long {
        try {
            return fitGroupIdString.toLong()
        } catch (exception: Exception) {
            throw BadRequestException("fit group must be long")
        }
    }

    private fun getFitMateDetails(fitGroupId: Long): FitMateDetailsResponseDto {
        val uriEndPoint = "${FitGroupUri.MATE_ROOT}/${fitGroupId}"

        val response: ResponseEntity<FitMateDetailsResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitMateDetailsResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit group response body is null")
    }
}