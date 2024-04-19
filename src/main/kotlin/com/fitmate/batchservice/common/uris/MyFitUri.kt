package com.fitmate.batchservice.common.uris

class MyFitUri {

    companion object {
        const val FIT_GROUP_ROOT_URI = "http://fit-group:8081/my-fit-service"

        const val FIT_CERTIFICATION_ROOT = "$FIT_GROUP_ROOT_URI/certifications"
        const val FIT_CERTIFICATION_PROGRESS = "$FIT_CERTIFICATION_ROOT/progresses"
    }
}
