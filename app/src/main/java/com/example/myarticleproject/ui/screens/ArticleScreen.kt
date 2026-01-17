package com.example.myarticleproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myarticleproject.ui.components.ArticleItem
import com.example.myarticleproject.viewmodel.ArticleViewModel
import com.example.myarticleproject.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(viewModel: ArticleViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My App title") })
        }
    ) { paddingValues ->

        Box(
            Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    val articles = (uiState as UiState.Success).articles
                    LazyColumn() {
                        items(articles) {
                            ArticleItem(article = it)
                        }
                    }
                }

                is UiState.Error -> {
                    val error = (uiState as UiState.Error).message
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.padding(8.dp))
                        Button(onClick = viewModel::loadArticles) {
                            Text("Retry")
                        }

                    }
                }
            }
        }

    }


}