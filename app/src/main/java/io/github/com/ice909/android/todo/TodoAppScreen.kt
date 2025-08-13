package io.github.com.ice909.android.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.com.ice909.android.todo.pages.DebugTaskScreen
import io.github.com.ice909.android.todo.pages.MainScreen
import io.github.com.ice909.android.todo.viewmodel.TodoViewModel

@Composable
fun TodoAppScreen(viewModel: TodoViewModel) {
    val navController = rememberNavController()
    val todos by viewModel.todoList.collectAsState()

    NavHost(navController, startDestination = "main" ) {
        composable("main") {
            MainScreen(viewModel, onDebugClick = { navController.navigate("debug") })
        }
        composable("debug"){
            DebugTaskScreen(viewModel, onBack = { navController.popBackStack() })
        }
    }
}

