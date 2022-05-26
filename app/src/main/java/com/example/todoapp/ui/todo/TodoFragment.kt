package com.example.todoapp.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.data.model.Todo
import com.example.todoapp.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoFragment: Fragment() {

    private var todoAdapter: TodoAdapter? = null

    private var binding: FragmentTodoBinding? = null

    private val viewModel: TodoViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect {

                when(it) {
                    is TodoViewState.Success -> {
                        binding?.progress?.isVisible = false
                        val todos = it.data
                        todoAdapter = TodoAdapter(todos)
                        binding?.rvTodo?.adapter = todoAdapter
                    }
                    is TodoViewState.Error -> {
                        binding?.progress?.isVisible = false
                        Toast.makeText(requireContext(), it.message,Toast.LENGTH_SHORT).show()
                    }
                    is TodoViewState.Loading -> {
                        binding?.progress?.isVisible = true
                    }
                }
            }
        }




    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}