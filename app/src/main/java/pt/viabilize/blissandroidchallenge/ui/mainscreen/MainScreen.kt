package pt.viabilize.blissandroidchallenge.ui.mainscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.NullRequestDataException
import coil3.request.crossfade
import pt.viabilize.blissandroidchallenge.R
import pt.viabilize.blissandroidchallenge.ui.mainscreen.components.AvatarSearchComponent


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onNavigateToEmojiList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.snackBarEvent) {
        viewModel.snackBarEvent.collect { mensagem ->
            snackbarHostState.showSnackbar(
                message = mensagem
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        MainScreenContents(
            modifier = Modifier.padding(innerPadding),
            viewState = uiState
        ) { mainScreenActions ->
            when (mainScreenActions) {
                is MainScreenActions.EmojiListButtonClick -> {
                    onNavigateToEmojiList()
                }
                else -> viewModel.onAction(mainScreenActions)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreenContents(
    modifier: Modifier = Modifier,
    viewState: MainScreenViewState,
    onAction: (MainScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarSearchComponent(
            textFieldState = rememberTextFieldState(
                initialText = viewState.searchQuery
            ),
            isSearching = viewState.isSearching,
            onSearch = { query ->
                onAction(MainScreenActions.SearchAvatar(query))
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewState.imageUrl)
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
                    if (state.result.throwable !is NullRequestDataException) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error loading image",
                            tint = Color.Red.copy(alpha = 0.3f)
                        )
                    }
                }
            )
            Button(
                onClick = {
                    onAction(MainScreenActions.RandomEmojiButtonAction)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.random_emoji)
                )
            }
            Button(
                onClick = {
                    onAction(MainScreenActions.EmojiListButtonClick)
                }
            ) {
                Text(
                    text = stringResource(R.string.emoji_list)
                )
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContents(
        viewState = MainScreenViewState(),
        onAction = { }
    )
}
