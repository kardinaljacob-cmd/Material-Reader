package com.example.reader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = dynamicLightColorScheme(this)) {
                ReaderScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen() {
    var selectedBook by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(selectedBook ?: "Библиотека") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { padding ->
        AnimatedContent(
            targetState = selectedBook,
            transitionSpec = { slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut() }
        ) { book ->
            if (book == null) {
                LazyColumn(Modifier.padding(padding)) {
                    items(listOf("Война и мир", "Мастер и Маргарита", "1984")) { title ->
                        ListItem(
                            headlineContent = { Text(title) },
                            supportingContent = { Text("Нажмите, чтобы читать") },
                            modifier = Modifier.clickable { selectedBook = title }
                        )
                    }
                }
            } else {
                Column(Modifier.padding(padding).padding(16.dp)) {
                    Text("Текст книги $book...", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = { selectedBook = null }) { Text("Закрыть") }
                }
            }
        }
    }
}
