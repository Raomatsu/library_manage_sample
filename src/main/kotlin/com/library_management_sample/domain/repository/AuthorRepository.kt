package com.library_management_sample.domain.repository

import com.library_management_sample.domain.model.Author

interface AuthorRepository {
    fun findById(id: Int): Author?

    fun findByName(name: String): List<Author>

    fun findAll(): List<Author>

    fun register(name: String): Int

    fun update(id: Int, name: String): Int

    fun getWithBooks(id: Int): Author?
}
