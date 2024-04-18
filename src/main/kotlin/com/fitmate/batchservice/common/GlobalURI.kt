package com.fitmate.batchservice.common

class GlobalURI {

    companion object {
        const val ROOT_URI: String = "/batch-service"

        const val QUARTZ_ROOT: String = "$ROOT_URI/quartz"
        const val QUARTZ_PAUSE: String = "$QUARTZ_ROOT/pause"
        const val QUARTZ_RESUME: String = "$QUARTZ_ROOT/resume"

        const val PATH_VARIABLE_JOB_NAME = "job-name"
        const val PATH_VARIABLE_JOB_NAME_WITH_BRACE = "/{$PATH_VARIABLE_JOB_NAME}"

        const val FIT_CERTIFICATION_RESULT_ROOT = "$ROOT_URI/certifications/results"

        const val PATH_VARIABLE_FIT_CERTIFICATION_ID = "fit-certification-id"
        const val PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_CERTIFICATION_ID}"
    }
}