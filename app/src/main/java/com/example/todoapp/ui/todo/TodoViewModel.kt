package com.example.todoapp.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val state: MutableStateFlow<TodoViewState>
        get() = MutableStateFlow<TodoViewState>(TodoViewState.Loading)

    init {
        getTodos()
    }

    private fun getTodos() {
        viewModelScope.launch {
            todoRepository.getTodos().let { result: Result<List<Todo>> ->
                when (result) {
                    is Result.Loading -> state.value = TodoViewState.Loading
                    is Result.Success -> state.value =
                        TodoViewState.Success(result.data ?: listOf())
                    is Result.Error -> state.value = TodoViewState.Error(result.message.orEmpty())
                }
            }
        }
    }
}

sealed class TodoViewState {
    object Loading : TodoViewState()
    data class Success(
        val data: List<Todo>
    ) : TodoViewState()

    data class Error(
        val message: String
    ) : TodoViewState()
}