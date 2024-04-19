package com.fitmate.batchservice.service

import com.fitmate.batchservice.event.event.RegisterFitPenaltyEvent
import com.fitmate.batchservice.exception.ResourceNotFoundException
import com.fitmate.batchservice.persistence.entity.CertificationStatus
import com.fitmate.batchservice.persistence.entity.FitGroupForRead
import com.fitmate.batchservice.persistence.entity.FitMateForRead
import com.fitmate.batchservice.persistence.entity.FitPenalty
import com.fitmate.batchservice.persistence.repository.FitCertificationResultRepository
import com.fitmate.batchservice.persistence.repository.FitGroupForReadRepository
import com.fitmate.batchservice.persistence.repository.FitPenaltyRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters


@Service
class FitPenaltyJobServiceImpl(
    private val fitGroupForReadRepository: FitGroupForReadRepository,
    private val fitPenaltyRepository: FitPenaltyRepository,
    private val fitCertificationResultRepository: FitCertificationResultRepository,
    private val eventPublisher: ApplicationEventPublisher
) : FitPenaltyJobService {

    override fun checkCertificationAndIssueFitPenalty(fitMateForRead: FitMateForRead): FitPenalty? {
        val fitGroup = fitGroupForReadRepository.findByFitGroupId(fitMateForRead.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group does not exist") }

        val now = Instant.now()

        val thisMonday =
            now.atZone(ZoneId.systemDefault()).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toInstant()

        val lastMonday = thisMonday.minus(7, ChronoUnit.DAYS)
        val lastSunday = thisMonday.minus(1, ChronoUnit.DAYS)

        val fitPenaltyOpt =
            fitPenaltyRepository.findByFitGroupIdAndUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(
                fitGroup.fitGroupId,
                fitMateForRead.fitMateUserId,
                lastMonday,
                lastSunday
            )

        if (fitPenaltyOpt.isPresent) return null

        val fitCertificationResult =
            fitCertificationResultRepository.findLastWeekCertificationsByUserAndFitGroupAndStatus(
                fitMateForRead.fitGroupId,
                fitMateForRead.fitMateUserId,
                lastMonday,
                lastSunday,
                CertificationStatus.CERTIFIED
            )

        return if (fitGroup.frequency > fitCertificationResult.size) createFitPenalty(fitMateForRead, fitGroup)
        else null
    }

    private fun createFitPenalty(
        fitMateForRead: FitMateForRead,
        fitGroup: FitGroupForRead
    ) = FitPenalty(
        fitMateForRead.fitMateUserId,
        fitGroup.fitGroupId,
        fitGroup.penaltyAmount,
        false
    )

    @Transactional
    override fun saveFitPenalty(fitPenalty: FitPenalty) {
        val savedFitPenalty = fitPenaltyRepository.save(fitPenalty)
        eventPublisher.publishEvent(RegisterFitPenaltyEvent(savedFitPenalty.id!!))
    }
}