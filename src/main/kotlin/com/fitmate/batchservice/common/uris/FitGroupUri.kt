package com.fitmate.batchservice.common.uris

class FitGroupUri {

    companion object {
        const val FIT_GROUP_ROOT_URI = "http://fit-group:8080/fit-group-service"

        const val GROUP_ROOT = "$FIT_GROUP_ROOT_URI/groups"
        const val MATE_ROOT = "$FIT_GROUP_ROOT_URI/mates"
    }
}
