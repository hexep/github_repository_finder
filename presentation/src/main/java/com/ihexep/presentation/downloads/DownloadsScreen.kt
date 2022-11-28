package com.ihexep.presentation.downloads

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ihexep.presentation.R
import com.ihexep.presentation.downloads.components.DownloadedRepositoryListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    navController: NavController
) {
    val mainViewModel = koinViewModel<DownloadsViewModel>()
    val downloadsState = mainViewModel.downloadsState.value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Column(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.downloads)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            scrollBehavior = scrollBehavior
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (downloadsState.isLoading) {
                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
            }
            if (downloadsState.error.isNotBlank()) {
                Text(
                    text = downloadsState.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (downloadsState.repositories.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(downloadsState.repositories) { repository ->
                        DownloadedRepositoryListItem(repository = repository)
                    }
                }
            }
            if (!downloadsState.isLoading &&
                downloadsState.error.isBlank() &&
                downloadsState.repositories.isEmpty()
            ) {
                Text(
                    text = stringResource(R.string.no_downloads),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}