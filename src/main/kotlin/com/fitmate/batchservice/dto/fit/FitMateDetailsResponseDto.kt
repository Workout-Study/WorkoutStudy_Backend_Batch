package com.fitmate.batchservice.dto.fit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitMateDetailsResponseDto(
    val fitGroupId: Long,
    val fitMateDetails: List<FitMateResponseDto>
)
