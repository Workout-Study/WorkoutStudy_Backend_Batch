package com.fitmate.batchservice.exception

class BadRequestException(override val message: String) : RuntimeException(message) {
}