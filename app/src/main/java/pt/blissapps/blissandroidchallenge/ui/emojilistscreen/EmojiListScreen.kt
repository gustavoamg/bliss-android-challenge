package pt.blissapps.blissandroidchallenge.ui.emojilistscreen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun EmojiListScreen(
    modifier: Modifier = Modifier,
    viewModel : EmojiListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //Returns the grid to the firs position after refreshing
    val gridState = rememberLazyGridState()
    var wasRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isLoading) {
        if (wasRefreshing && !uiState.isLoading) {
            gridState.animateScrollToItem(index = 0, scrollOffset = 0)
        }
        wasRefreshing = uiState.isLoading
    }

    PullToRefreshBox(
        isRefreshing = uiState.isLoading,
        onRefresh = {
            viewModel.loadEmojis()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.FixedSize(size = 80.dp),
            state = gridState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),

        ) {
            items(
                items = uiState.emojiList,
                key = { it.name }
            ) { emoji ->
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(emoji.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Dynamic Emoji",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                        .clickable(
                            onClick = {
                                viewModel.removeFromList(emoji)
                            }
                        )
                        .animateItem(
                            fadeOutSpec = tween(durationMillis = 400),
                            placementSpec = tween(durationMillis = 300)
                        )
                        .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
                    // Displays while the network request is pending
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    },
                    // Displays if the network fails, or if the URL is malformed/404
                    error = { state ->
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error loading image",
                            tint = Color.Red.copy(alpha = 0.3f)
                        )
                    }
                )
            }
        }
    }
}