package com.example.todoapp.data.service

import com.example.todoapp.data.model.Todo
import retrofit2.http.GET

interface PlaceHolderService {

    @GET("todos")
    suspend fun fetchTodos(): List<Todo>
}