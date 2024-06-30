package com.library_management_sample.application.param.author

import jakarta.validation.constraints.NotEmpty

data class AuthorPostOrPutRequestBody(
    @field:NotEmpty(message = "nameは必須パラメータです")
    val name: String?,
)
