package com.ihexep.presentation.features.downloads.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.presentation.R

@Composable
fun DownloadedRepositoryListItem(
    repository: GithubRepositoryModel,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.downloaded_repository_owner, repository.owner),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.downloaded_repository_name, repository.name),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}