package io.github.com.ice909.android.todo.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.com.ice909.android.todo.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugTaskScreen(viewModel: TodoViewModel, onBack: () -> Unit = {}) {
    val todos by viewModel.todoList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("任务调试页") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("ID: ${task.id}", style = MaterialTheme.typography.labelSmall)
                        Text("标题: ${task.title}", style = MaterialTheme.typography.titleMedium)
                        Text("内容: ${task.content}", style = MaterialTheme.typography.bodySmall)
                        Text("父ID: ${task.parentId}", style = MaterialTheme.typography.bodySmall)
                        Text("排序: ${task.sortOrder}", style = MaterialTheme.typography.bodySmall)
                        Text("已完成: ${task.completed}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}