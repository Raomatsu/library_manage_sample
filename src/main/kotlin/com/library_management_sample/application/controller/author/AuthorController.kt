package com.library_management_sample.application.controller.author

import com.library_management_sample.application.param.author.AuthorPostOrPutRequestBody
import com.library_management_sample.application.view.author.AuthorSearchView
import com.library_management_sample.application.view.author.AuthorsBookSearchView
import com.library_management_sample.domain.service.author.AuthorService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class AuthorController(
    private val authorService: AuthorService
) {
    @GetMapping("/v1/authors")
    fun get(
        @RequestParam
        id: Int?,
        @RequestParam
        name: String?,
    ): AuthorSearchView {
        return if (id == null && name.isNullOrBlank()) {
            // クエリパラメータの指定がない場合は全件検索
            authorService.findAll()
        } else if (id != null) {
            // idが指定されている場合はidで検索
            authorService.findById(id)
        } else {
            // 書籍名のみ指定されている場合は名前の部分一致検索
            authorService.findByName(name!!)
        }
    }

    @GetMapping("/v1/authors/{authorId}/books")
    fun getWithBooks(
        @PathVariable
        authorId: Int,
    ): AuthorsBookSearchView {
        return authorService.getWithBooks(authorId)
    }


    @PostMapping("/v1/authors")
    fun post(
        @RequestBody
        @Valid
        requestBody: AuthorPostOrPutRequestBody?
    ) {
        authorService.register(requestBody!!.name!!)
    }

    @PutMapping("/v1/authors/{authorId}")
    fun put(
        @PathVariable
        authorId: Int,
        @RequestBody
        @Valid
        requestBody: AuthorPostOrPutRequestBody?
    ) {
        authorService.update(authorId, requestBody!!.name!!)
    }
}