package com.ihexep.presentation.features.search

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ihexep.presentation.R
import com.ihexep.presentation.common.Screen
import com.ihexep.presentation.features.search.components.RepositoryListItem
import com.ihexep.presentation.features.search.components.SearchAppBar
import com.ihexep.presentation.utils.toast
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = koinViewModel<SearchViewModel>()
    val searchState by remember { viewModel.searchState }
    val downloadState = viewModel.downloadState
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val writePermissionState = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) { viewModel.errorState.collect { context.toast(it) } }
    Column(modifier = Modifier
        .nestedScroll(scrollBehavior.nestedScrollConnection)
        .background(MaterialTheme.colorScheme.primary)
        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        SearchAppBar(
            onActionSearch = { viewModel.getRepositories(it) },
            onActionGoToDownloads = { navController.navigate(Screen.DownloadsScreen.route) },
            scrollBehavior = scrollBehavior
        )
        if (searchState.isLoading) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.tertiary,
                trackColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchState.repositories) { repository ->
                    RepositoryListItem(
                        repository = repository,
                        state = downloadState,
                        onClick = {
                            focusManager.clearFocus()
                            uriHandler.openUri(it.url)
                        },
                        onDownloadClick = {
                            focusManager.clearFocus()
                            if (writePermissionState.status.isGranted) {
                                viewModel.downloadRepository(it)
                            } else {
                                writePermissionState.launchPermissionRequest()
                            }
                        }
                    )
                }
            }
            if (searchState.error.isNotBlank()) {
                Text(
                    text = if (searchState.error.trim() == "HTTP 404")
                        stringResource(R.string.user_not_found) else searchState.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}