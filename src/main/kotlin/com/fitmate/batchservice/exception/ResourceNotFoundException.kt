package com.fitmate.batchservice.exception

class ResourceNotFoundException(override val message: String) : RuntimeException(message) {
}