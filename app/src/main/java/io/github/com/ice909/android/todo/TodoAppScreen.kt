package io.github.com.ice909.android.todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.com.ice909.android.todo.model.Task
import io.github.com.ice909.android.todo.ui.components.Chip
import io.github.com.ice909.android.todo.ui.components.CustomCheckbox
import io.github.com.ice909.android.todo.viewmodel.TodoViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoAppScreen(viewModel: TodoViewModel) {
    val todos by viewModel.todoList.collectAsState()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Add, contentDescription = "add")
        }
    }, modifier = Modifier.statusBarsPadding()) {
        Column(Modifier.fillMaxSize()) {
            Text(
                "待办清单",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text("10 待完成", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.width(8.dp))
                Text("2 已完成", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Chip(
                text = "3 个重要任务",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            HorizontalDivider(
                Modifier.padding(vertical = 4.dp),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                items(todos) { item ->
                    TaskItem(
                        item,
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        CustomCheckbox(
            checked = task.completed,
            onCheckedChange = { ->
                // 这里写你切换完成状态的逻辑，比如
                viewModel.toggleCompleted(task.id)
            }
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (task.completed) TextDecoration.LineThrough else null
            )
            if (task.content.isNotBlank()) {
                Text(
                    text = task.content,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textDecoration = if (task.completed) TextDecoration.LineThrough else null
                )
            }
        }
    }
}