package io.github.com.ice909.android.todo.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.com.ice909.android.todo.model.Task
import io.github.com.ice909.android.todo.ui.components.Chip
import io.github.com.ice909.android.todo.ui.components.CustomCheckbox
import io.github.com.ice909.android.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: TodoViewModel, onDebugClick: () -> Unit = {}) {
    val todos by viewModel.todoList.collectAsState()
    val activeCount by viewModel.activeCount.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()
    val importantCount by viewModel.importantCount.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var newTitle by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { showSheet = true }) {
            Icon(Icons.Default.Add, contentDescription = "add")
        }
    }, modifier = Modifier.statusBarsPadding()) {
        Column(Modifier.fillMaxSize()) {
            Text(
                "待办清单",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onDebugClick)
            )
            Row(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text("$activeCount 待完成", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.width(8.dp))
                Text("$completedCount 已完成", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Chip(
                text = "$importantCount 个重要任务",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            HorizontalDivider(
                Modifier.padding(vertical = 12.dp),
                DividerDefaults.Thickness,
                color = Color(0x1A000000)
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
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
            },
            sheetState = sheetState
        ) {
            Column(Modifier.padding(top = 0.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)) {
                Text("添加新任务", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = { Text("任务标题") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("输入任务标题...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.5f
                        ),
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
                Spacer(Modifier.height(20.dp))
                Row {
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                                showSheet = false
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) { Text("取消") }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (newTitle.isNotBlank()) {
                                viewModel.addTask(
                                    Task(
                                        id = java.util.UUID.randomUUID().toString(),
                                        title = newTitle,
                                        content = "",
                                        parentId = "",
                                        sortOrder = 0,
                                        completed = false
                                    )
                                )
                                newTitle = ""
                                coroutineScope.launch {
                                    sheetState.hide()
                                    showSheet = false
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) { Text("添加") }
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