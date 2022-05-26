package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.utils.Result

interface TodoRepository {

    suspend fun getTodos(): Result<List<Todo>>
}