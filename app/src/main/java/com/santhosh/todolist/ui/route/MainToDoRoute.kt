package com.santhosh.todolist.ui.route

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santhosh.core.data.model.ToDo
import com.santhosh.todolist.ui.theme.ToDoListTheme
import com.santhosh.todolist.viewmodel.MainToDoViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MainToDoRoute(onFabClick: () -> Unit, viewModel: MainToDoViewModel = hiltViewModel()) {
    MainToDoScreen(
        state = viewModel.todos.collectAsState(),
        searchQuery = viewModel.searchQuery.collectAsState().value,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onFabClick = onFabClick,
    )
}

@Composable
private fun MainToDoScreen(
    state: State<List<ToDo>>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search ToDo's") }
            )
            Spacer(Modifier.height(12.dp))

            if (state.value.isEmpty()) {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        "Press the + button to add a TODO item",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.value) { toDoItem ->
                        ToDoItem(toDoItem)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }

    }
}

@Composable
private fun ToDoItem(toDoItem: ToDo) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                toDoItem.title,
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedVisibility(visible = expanded && !toDoItem.description.isNullOrEmpty()) {
                Text(
                    toDoItem.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MainToDoScreenPreview() {
    ToDoListTheme {
        MainToDoScreen(
            state = MutableStateFlow<List<ToDo>>(emptyList()).collectAsState(),
            searchQuery = "",
            onSearchQueryChange = {},
            onFabClick = {})
    }
}