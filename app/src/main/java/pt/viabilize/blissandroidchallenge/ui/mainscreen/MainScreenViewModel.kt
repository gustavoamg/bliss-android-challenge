package pt.viabilize.blissandroidchallenge.ui.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import pt.viabilize.blissandroidchallenge.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val gitHubRepository: GitHubRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenViewState())
    val uiState: StateFlow<MainScreenViewState> = _uiState.asStateFlow()


    fun onAction(viewActions: MainScreenActions) {
        Log.d("VIEW_INTERACTION", "Button clicked: " + viewActions::class.java)
        when(viewActions) {
            is MainScreenActions.FetchEmojisButtonAction -> fetchEmojis()
        }
    }

    fun fetchEmojis() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy (
                    emojiList = gitHubRepository.getEmojiList(),
                    isLoading = false
                )
            }
        }
    }
}