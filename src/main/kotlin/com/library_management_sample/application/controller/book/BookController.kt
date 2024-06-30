package com.library_management_sample.application.controller.book

import com.library_management_sample.application.exception.InvalidRequestException
import com.library_management_sample.application.param.book.BookPostOrPutRequestBody
import com.library_management_sample.application.view.book.BookSearchView
import com.library_management_sample.domain.service.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService,
) {
    @GetMapping("/v1/books")
    fun get(
        @RequestParam
        id: Int?,
        @RequestParam
        name: String?,
    ): BookSearchView {
        return if (id == null && name.isNullOrBlank()) {
            // クエリパラメータの指定がない場合は全件検索
            bookService.findAll()
        } else if (id != null) {
            // idが指定されている場合はidで検索
            bookService.findById(id)
        } else {
            // 書籍名のみ指定されている場合は名前の部分一致検索
            bookService.findByName(name!!)
        }
    }

    @PostMapping("/v1/books")
    fun post(
        @RequestBody
        requestBody: BookPostOrPutRequestBody?
    ) {
        if (requestBody?.validateOnPost() != true ) {
            // バリデーションチェック
            throw InvalidRequestException(
                requestBody?.let {
                    mapOf(
                        "name" to it.name.toString(),
                        "authorId" to it.authorId.toString()
                    )
                }
            )
        }
        bookService.register(requestBody.name!!, requestBody.authorId!!)
    }

    @PutMapping("/v1/books/{bookId}")
    fun put(
        @PathVariable
        bookId: Int?,
        @RequestBody
        requestBody: BookPostOrPutRequestBody?
    ) {
        if (bookId == null || requestBody?.validateOnPut() != true) {
            // バリデーションチェック
            throw InvalidRequestException(
                requestBody?.let {
                    mapOf(
                        "bookId" to bookId.toString(),
                        "name" to it.name.toString(),
                        "authorId" to it.authorId.toString()
                    )
                }
            )
        }
        bookService.update(bookId, requestBody.name, requestBody.authorId)
    }
}