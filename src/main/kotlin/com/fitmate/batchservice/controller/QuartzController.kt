package com.fitmate.batchservice.controller

import com.fitmate.batchservice.common.GlobalURI
import com.fitmate.batchservice.dto.quartz.*
import com.fitmate.batchservice.service.QuartzService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class QuartzController(private val quartzService: QuartzService) {

    /**
     * register Job
     *
     * @param registerQuartzRequestDto job name and cron
     * @return Boolean about register success
     */
    @PostMapping(GlobalURI.QUARTZ_ROOT)
    fun registerJob(@RequestBody @Valid registerQuartzRequestDto: RegisterQuartzRequestDto): ResponseEntity<RegisterQuartzResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartzService.registerJob(registerQuartzRequestDto))
    }

    /**
     * update Job
     *
     * @param requestDto
     * @return
     */
    @PutMapping("${GlobalURI.QUARTZ_ROOT}${GlobalURI.PATH_VARIABLE_JOB_NAME_WITH_BRACE}")
    fun updateJob(
        @PathVariable(GlobalURI.PATH_VARIABLE_JOB_NAME) jobName: String,
        @RequestBody @Valid updateQuartzRequestDto: UpdateQuartzRequestDto
    ): ResponseEntity<UpdateQuartzResponseDto> {
        return ResponseEntity.ok().body(quartzService.updateJob(jobName, updateQuartzRequestDto))
    }

    /**
     * delete Job
     *
     * @param requestDto
     * @return
     */
    @DeleteMapping("${GlobalURI.QUARTZ_ROOT}${GlobalURI.PATH_VARIABLE_JOB_NAME_WITH_BRACE}")
    fun deleteJob(
        @PathVariable(GlobalURI.PATH_VARIABLE_JOB_NAME) jobName: String,
        @RequestBody @Valid deleteQuartzRequestDto: DeleteQuartzRequestDto
    ): ResponseEntity<DeleteQuartzResponseDto> {
        return ResponseEntity.ok().body(quartzService.deleteJob(jobName, deleteQuartzRequestDto))
    }

    /**
     * pause Job
     *
     * @param jobName request pause job name
     * @return Boolean about pause success
     */
    @PutMapping("${GlobalURI.QUARTZ_PAUSE}${GlobalURI.PATH_VARIABLE_JOB_NAME_WITH_BRACE}")
    fun pauseJob(
        @PathVariable(GlobalURI.PATH_VARIABLE_JOB_NAME) jobName: String,
        @RequestBody @Valid pauseQuartzRequestDto: PauseQuartzRequestDto
    ): ResponseEntity<PauseQuartzResponseDto> {
        return ResponseEntity.ok().body(quartzService.pauseJob(jobName, pauseQuartzRequestDto))
    }

    /**
     * resume Job
     *
     * @param jobName
     * @return
     */
    @PutMapping("${GlobalURI.QUARTZ_RESUME}${GlobalURI.PATH_VARIABLE_JOB_NAME_WITH_BRACE}")
    fun resumeJob(
        @PathVariable(GlobalURI.PATH_VARIABLE_JOB_NAME) jobName: String,
        @RequestBody @Valid resumeQuartzRequestDto: ResumeQuartzRequestDto
    ): ResponseEntity<ResumeQuartzResponseDto> {
        return ResponseEntity.ok().body(quartzService.resumeJob(jobName, resumeQuartzRequestDto))
    }
}