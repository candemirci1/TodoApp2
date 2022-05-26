package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.service.PlaceHolderService
import com.example.todoapp.data.utils.Result
import retrofit2.HttpException
import java.io.IOException

class TodoRepositoryImpl(private val service: PlaceHolderService): TodoRepository {

    override suspend fun getTodos(): Result<List<Todo>>{
        return try {
            val todos = service.fetchTodos()
            Result.Success(todos)
        } catch (e: HttpException) {
            Result.Error(e.message.orEmpty(),e.code())
        } catch (e: IOException) {
            Result.Error("check your internet connection")
        }
    }


}