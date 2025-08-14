package io.github.com.ice909.android.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.com.ice909.android.todo.ui.theme.TodoTheme
import io.github.com.ice909.android.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TodoViewModel by viewModels {
        object : ViewModelProvider.AndroidViewModelFactory(application), ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return TodoViewModel(application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                TodoAppScreen(viewModel)
            }
        }
    }
}