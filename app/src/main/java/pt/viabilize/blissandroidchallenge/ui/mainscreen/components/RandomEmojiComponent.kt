package pt.viabilize.blissandroidchallenge.ui.mainscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.NullRequestDataException
import coil3.request.crossfade
import pt.viabilize.blissandroidchallenge.R
import pt.viabilize.blissandroidchallenge.model.Emoji

@Composable
fun RandomEmojiComponent(
    modifier: Modifier = Modifier,
    emoji: Emoji? = null,
    onButtonClick: () -> Unit
) {
    Surface (
        shape = RoundedCornerShape( 32.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(emoji?.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Dynamic Emoji",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp),
                // Displays while the network request is pending
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                },
                // Displays if the network fails, or if the URL is malformed/404
                error = { state ->
                    if(state.result.throwable !is NullRequestDataException) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error loading image",
                            tint = Color.Red.copy(alpha = 0.3f)
                        )
                    }
                }
            )
            Button(
                onClick = onButtonClick
            ) {
                Text(
                    text = stringResource(id = R.string.random_emoji)
                )
            }
        }
    }
}

@Preview
@Composable
fun RandomEmojiComponentPreview() {
    RandomEmojiComponent(
        onButtonClick = {}
    )
}