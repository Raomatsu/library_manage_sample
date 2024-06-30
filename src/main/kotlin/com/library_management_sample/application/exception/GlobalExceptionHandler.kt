package com.library_management_sample.application.exception

import com.library_management_sample.application.view.error.ErrorView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException::class)
    fun handleValidationExceptions(e: InvalidRequestException): ResponseEntity<ErrorView> {
        val error = ErrorView(
            code = "400-0001",
            detailMessage = e.fieldToValue.toString()
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleValidationExceptions(e: ResourceNotFoundException): ResponseEntity<ErrorView> {
        val error = ErrorView(
            code = "404-0001",
            detailMessage = "指定されたリソースが見つかりませんでした"
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }
}