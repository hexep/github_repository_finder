package com.ihexep.presentation.search.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihexep.presentation.R
import com.ihexep.presentation.common.AnySizeTextField

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    onActionSearch: (String) -> Unit,
    onActionGoToDownloads: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    var searchValue by rememberSaveable { mutableStateOf("") }
    var isSearchBarTyping by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val transition = updateTransition(targetState = isSearchBarTyping, label = null)

    val searchBarWeight by transition.animateFloat(
        label = "barWeight",
        transitionSpec = { tween(durationMillis = 400) },
        targetValueByState = { if (it) 0.7f else 1f }
    )
    val iconSize by transition.animateDp(
        label = "iconSize",
        transitionSpec = { tween(durationMillis = 300) },
        targetValueByState = { if (it) 32.dp else 0.dp }
    )
    val startIconOffset by transition.animateDp(
        label = "startIconOffset",
        transitionSpec = { tween(durationMillis = 300) },
        targetValueByState = { if (it) 0.dp else (-50).dp }
    )
    val endIconOffset by transition.animateDp(
        label = "endIconOffset",
        transitionSpec = { tween(durationMillis = 200) },
        targetValueByState = { if (it) 0.dp else 50.dp }
    )

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.app_logo),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(iconSize)
                        .offset(startIconOffset)
                )
                IconButton(
                    onClick = { onActionGoToDownloads() },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(iconSize)
                        .offset(endIconOffset)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_downloads),
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = "Downloads",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                AnySizeTextField(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(percent = 50)
                        )
                        .fillMaxWidth(searchBarWeight)
                        .height(36.dp)
                        .onFocusChanged { isSearchBarTyping = !it.hasFocus },
                    value = searchValue,
                    valueStyle = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    hint = stringResource(R.string.enter_user_name),
                    hintStyle = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = !isSearchBarTyping, enter = fadeIn(), exit = fadeOut()
                        ) {
                            IconButton(onClick = { searchValue = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    },
                    onValueChange = { searchValue = it },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            if (searchValue.isNotEmpty()) onActionSearch(searchValue)
                        }
                    )
                )
            }
        }
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewSearchAppBar() {
    SearchAppBar(onActionSearch = {}, onActionGoToDownloads = {})
}