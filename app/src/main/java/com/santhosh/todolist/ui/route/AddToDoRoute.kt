package com.santhosh.todolist.ui.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santhosh.core.data.model.ToDo
import com.santhosh.todolist.ui.states.AddToDoUiState
import com.santhosh.todolist.ui.theme.ToDoListTheme
import com.santhosh.todolist.viewmodel.AddToDoViewModel

@Composable
fun AddToDoRoute(
    navigateBack: () -> Unit,
    viewModel: AddToDoViewModel = hiltViewModel()
) {
    AddToDoScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        addTodo = {
            viewModel.addTodo(navigateBack)
        }
    )
}

@Composable
private fun AddToDoScreen(
    uiState: AddToDoUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    addTodo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            OutlinedTextField(
                value = uiState.toDo.title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title") },
                placeholder = { Text("Enter TODO title") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.toDo.description ?: "",
                onValueChange = onDescriptionChange,
                label = { Text("Description (optional)") },
                placeholder = { Text("Enter TODO description") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = addTodo,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Add TODO") }
        }
        if (uiState.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
private fun AddToDoScreenPreview() {
    ToDoListTheme {
        AddToDoScreen(
            uiState = AddToDoUiState(
                toDo = ToDo(
                    1, "title",
                    "description"
                )
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            addTodo = {}
        )
    }
}