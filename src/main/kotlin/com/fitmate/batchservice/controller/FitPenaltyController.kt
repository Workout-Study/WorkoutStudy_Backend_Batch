package com.fitmate.batchservice.controller

import com.fitmate.batchservice.common.GlobalURI
import com.fitmate.batchservice.dto.penalty.FitPenaltyDetailResponse
import com.fitmate.batchservice.service.FitPenaltyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FitPenaltyController(
    private val fitPenaltyService: FitPenaltyService
) {

    @GetMapping(GlobalURI.FIT_PENALTY_ROOT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE)
    fun getFitPenalty(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID) fitPenaltyId: Long
    ): ResponseEntity<FitPenaltyDetailResponse> =
        ResponseEntity.ok().body(fitPenaltyService.getFitPenalty(fitPenaltyId))
}