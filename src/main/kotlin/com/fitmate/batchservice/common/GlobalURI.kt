package com.fitmate.batchservice.common

class GlobalURI {

    companion object {
        const val QUARTZ_ROOT: String = "/quartz"
        const val QUARTZ_PAUSE: String = "$QUARTZ_ROOT/pause"
        const val QUARTZ_RESUME: String = "$QUARTZ_ROOT/resume"

        const val PATH_VARIABLE_JOB_NAME = "job-name"
        const val PATH_VARIABLE_JOB_NAME_WITH_BRACE = "/{$PATH_VARIABLE_JOB_NAME}"
    }
}