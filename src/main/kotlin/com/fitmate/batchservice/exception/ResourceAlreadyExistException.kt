package com.fitmate.batchservice.exception

class ResourceAlreadyExistException(override val message: String) : RuntimeException(message) {
}