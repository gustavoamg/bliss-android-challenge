package pt.viabilize.blissandroidchallenge.ui.mainscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pt.viabilize.blissandroidchallenge.R


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel : MainScreenViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContents(
        viewState = uiState
    ) { mainScreenActions ->
        viewModel.onAction(mainScreenActions)
    }
}

@Composable
fun MainScreenContents(
    modifier: Modifier = Modifier,
    viewState: MainScreenViewState,
    onAction: (MainScreenActions) -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(viewState.isLoading) {
            CircularProgressIndicator()
        }
        else {
            Button(
                onClick = {
                    onAction(MainScreenActions.FetchEmojisButtonAction)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.fetch_emojis)
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
