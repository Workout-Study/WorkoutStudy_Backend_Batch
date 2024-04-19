package com.fitmate.batchservice.controller

import com.fitmate.batchservice.common.GlobalURI
import com.fitmate.batchservice.dto.certification.FitCertificationResultResponse
import com.fitmate.batchservice.service.FitCertificationResultService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FitCertificationResultController(
    private val fitCertificationResultService: FitCertificationResultService
) {

    @GetMapping(GlobalURI.FIT_CERTIFICATION_RESULT_ROOT + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE)
    fun getFitCertificationResult(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID) fitCertificationId: Long
    ): ResponseEntity<FitCertificationResultResponse> =
        ResponseEntity.ok().body(fitCertificationResultService.getFitCertificationResult(fitCertificationId))
}