package com.example.newsapp.news.sealed

import com.example.newsapp.news.models.News

sealed class DataState {
    class Success(val data: MutableList<News>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}