package com.fitmate.batchservice.common.uris

class MyFitUri {

    companion object {
        const val MY_FIT_ROOT_URI = "http://my-fit:8081/my-fit-service"

        const val FIT_CERTIFICATION_ROOT = "$MY_FIT_ROOT_URI/certifications"
        const val FIT_CERTIFICATION_PROGRESS = "$FIT_CERTIFICATION_ROOT/progresses"
    }
}
