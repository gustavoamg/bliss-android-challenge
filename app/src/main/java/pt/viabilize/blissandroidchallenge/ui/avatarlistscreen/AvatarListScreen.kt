package pt.viabilize.blissandroidchallenge.ui.avatarlistscreen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import pt.viabilize.blissandroidchallenge.R

@Composable
fun AvatarListScreen(
    modifier: Modifier = Modifier,
    viewModel : AvatarListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    if(uiState.avatarList.isEmpty()) {
        Text(modifier = Modifier.fillMaxWidth()
            .padding(32.dp),
            text = stringResource(R.string.no_avatar_available),
            textAlign = TextAlign.Center
        )
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.FixedSize(size = 80.dp),
            state = gridState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
            items(
                items = uiState.avatarList,
                key = { it.id }
            ) { avatar ->
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                        .clickable(
                            onClick = {
                                viewModel.removeFromList(avatar)
                            }
                        )
                        .animateItem(
                            fadeOutSpec = tween(durationMillis = 400),
                            placementSpec = tween(durationMillis = 300)
                        ),
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