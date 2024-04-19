package com.fitmate.batchservice.exception

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(badRequestException: BadRequestException): ResponseEntity<String> {
        logger?.info("BadRequestException ", badRequestException)
        return ResponseEntity.badRequest().body(badRequestException.message)
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerException(nullPointerException: NullPointerException): ResponseEntity<String> {
        logger?.info("NullPointerException ", nullPointerException)
        return ResponseEntity.badRequest().body(nullPointerException.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(methodArgumentNotValidException: MethodArgumentNotValidException): ResponseEntity<String> {
        logger?.info("MethodArgumentNotValidException", methodArgumentNotValidException)
        return ResponseEntity.badRequest().body(methodArgumentNotValidException.message)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(resourceNotFoundException: ResourceNotFoundException): ResponseEntity<String> {
        logger?.info("ResourceNotFoundException", resourceNotFoundException)
        return ResponseEntity.badRequest().body(resourceNotFoundException.message)
    }

    @ExceptionHandler(ResourceAlreadyExistException::class)
    fun resourceAlreadyExistException(resourceAlreadyExistException: ResourceAlreadyExistException): ResponseEntity<String> {
        logger?.info("ResourceAlreadyExistException", resourceAlreadyExistException)
        return ResponseEntity.badRequest().body(resourceAlreadyExistException.message)
    }

    @ExceptionHandler(NotExpectResultException::class)
    fun notExpectResultException(notExpectResultException: NotExpectResultException): ResponseEntity<String> {
        logger?.info("NotExpectResultException", notExpectResultException)
        return ResponseEntity.badRequest().body(notExpectResultException.message)
    }
}