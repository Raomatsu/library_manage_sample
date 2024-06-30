package com.library_management_sample.domain.service.author

import com.library_management_sample.application.exception.ResourceNotFoundException
import com.library_management_sample.application.view.author.AuthorSearchView
import com.library_management_sample.application.view.author.AuthorSearchViewFactory
import com.library_management_sample.application.view.author.AuthorsBookSearchView
import com.library_management_sample.application.view.author.AuthorsBookSearchViewFactory
import com.library_management_sample.domain.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {
    fun findById(id: Int): AuthorSearchView {
        val result = authorRepository.findById(id) ?: throw ResourceNotFoundException()
        return AuthorSearchViewFactory.create(listOf(result))
    }

    fun findByName(name: String): AuthorSearchView {
        val result = authorRepository.findByName(name)
        if (result.isEmpty()) throw ResourceNotFoundException()
        return AuthorSearchViewFactory.create(result)
    }

    fun findAll(): AuthorSearchView {
        val result = authorRepository.findAll()
        if (result.isEmpty()) throw ResourceNotFoundException()
        return AuthorSearchViewFactory.create(result)
    }

    fun getWithBooks(id: Int): AuthorsBookSearchView {
        val result = authorRepository.getWithBooks(id) ?: throw ResourceNotFoundException()
        // 書籍が一冊もない場合は空のリスト
        return AuthorsBookSearchViewFactory.create(result)
    }

    fun register(name: String) {
        authorRepository.register(name)
    }

    fun update(id: Int, name: String) {
        val isExists = authorRepository.findById(id) != null
        if (!isExists) throw ResourceNotFoundException()

        authorRepository.update(id, name)
    }
}