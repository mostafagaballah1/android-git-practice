package com.example.myarticleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myarticleproject.data.ArticleRepository
import com.example.myarticleproject.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UiState {
    data class Success(val articles: List<Article>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()
}

class ArticleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadArticles() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                articleRepository.getArticles().onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                e.message?.let { _uiState.value = UiState.Error(it) }
            }
        }
    }

}