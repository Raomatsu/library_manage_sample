package com.library_management_sample.domain.repository

import com.library_management_sample.domain.model.Book

interface BookRepository {
    fun findById(id: Int): Book?

    fun findByName(name: String): List<Book>

    fun findAll(): List<Book>

    fun register(name: String, authorId: Int): Int

    fun update(id: Int, name: String?, authorId: Int?): Int

    fun findByAuthor(authorId: Int): List<Book>
}
