package com.santhosh.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.santhosh.core.common.AppEventBus
import com.santhosh.core.navigation.ktx.addOnDestinationChangedListener
import com.santhosh.todolist.ui.navigation.MainToDoDestination
import com.santhosh.todolist.ui.navigation.navigateToAddToDoRoute
import com.santhosh.todolist.ui.navigation.todoListGraph
import com.santhosh.todolist.ui.theme.ToDoListTheme
import com.santhosh.todolist.viewmodel.ToDoListActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                AppRoot()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot() {
    val viewModel: ToDoListActivityViewModel = viewModel()
    val navHostController: NavHostController = rememberNavController().apply {
        addOnDestinationChangedListener(viewModel::setTopBarData)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val topBarDataState = viewModel.topBarState.collectAsStateWithLifecycle()
    viewModel.errorBannerState.collectAsStateWithLifecycle()
    rememberCoroutineScope()
    LaunchedEffect(Unit) {
        AppEventBus.errors.collect { msg ->
            snackBarHostState.showSnackbar(msg)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    title = { Text(topBarDataState.value.toolBarTitle) }
                )
            }) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                NavHost(
                    navController = navHostController,
                    startDestination = MainToDoDestination.destination
                ) {
                    todoListGraph(
                        navigateBack = navHostController::popBackStack,
                        navigateToAddToDo = navHostController::navigateToAddToDoRoute
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun AppRootPreview() {
    ToDoListTheme {
        AppRoot()
    }
}