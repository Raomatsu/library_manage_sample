package com.library_management_sample.application.exception

class InvalidRequestException(
    val fieldToValue: Map<String,String>?,
) : Exception()
