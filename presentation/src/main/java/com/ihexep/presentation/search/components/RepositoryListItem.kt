package com.ihexep.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.presentation.R
import com.ihexep.presentation.search.RepositoryDownloadState

@Composable
fun RepositoryListItem(
    repository: GithubRepositoryModel,
    state: Map<Int, RepositoryDownloadState>,
    onClick: (GithubRepositoryModel) -> Unit,
    onDownloadClick: (GithubRepositoryModel) -> Unit,
) {
    val downloadState = state[repository.hashCode()] ?: RepositoryDownloadState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick(repository) }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_open_in_browser),
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = repository.name,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1.0f)
                .padding(10.dp)
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)) {
            if (downloadState.isDownloading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (downloadState.isDownloaded) {
                IconButton(modifier = Modifier.size(48.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download_done),
                        tint = Color(0xFF304B30),
                        contentDescription = null
                    )
                }
            }
            if (!downloadState.isDownloading && !downloadState.isDownloaded) {
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.0F)
                        .background(Color(0xFF304B30)),
                    onClick = { onDownloadClick(repository) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download),
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}