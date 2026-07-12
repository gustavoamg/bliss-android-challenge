package pt.viabilize.blissandroidchallenge.ui.repolistscreen.components

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.viabilize.blissandroidchallenge.model.Repo
import java.util.Date

@Composable
fun RepositoryItemComponent(
    modifier: Modifier = Modifier,
    repository: Repo
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
        ) {
            Column(
                modifier.fillMaxWidth()
                    .padding(16.dp)

            ) {
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = repository.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconAndText(
                        modifier = Modifier.wrapContentSize(),
                        icon = Icons.Default.RemoveRedEye,
                        text = repository.watchersCount.toString()
                    )

                    IconAndText(
                        modifier = Modifier.wrapContentSize(),
                        icon = Icons.Default.ForkRight,
                        text = repository.forks.toString()
                    )

                    IconAndText(
                        modifier = Modifier.wrapContentSize(),
                        icon = Icons.Default.Update,
                        text = DateUtils.getRelativeTimeSpanString(
                            repository.updatedAt.time,
                            System.currentTimeMillis(),
                            Long.MIN_VALUE
                        ).toString()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RepositoryItemComponentPreview() {
    RepositoryItemComponent(
        repository = Repo(
            id = 1L,
            name = "Test Repo",
            description = "Test repo description",
            updatedAt = Date(),
            watchersCount = 10,
            forks = 2
        )
    )
}