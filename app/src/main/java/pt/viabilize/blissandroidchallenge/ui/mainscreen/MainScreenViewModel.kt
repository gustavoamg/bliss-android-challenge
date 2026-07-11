package pt.viabilize.blissandroidchallenge.ui.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.viabilize.blissandroidchallenge.model.Emoji
import pt.viabilize.blissandroidchallenge.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val gitHubRepository: GitHubRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenViewState())
    val uiState: StateFlow<MainScreenViewState> = _uiState.asStateFlow()
    var cachedEmojiList: List<Emoji> = emptyList()

    private val _snackBarEvent = Channel<String>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    fun onAction(viewActions: MainScreenActions) {
        Log.d("VIEW_INTERACTION", "Button clicked: " + viewActions::class.java)
        when(viewActions) {
            is MainScreenActions.RandomEmojiButtonAction -> {
                viewModelScope.launch {
                    val fetchEmojisJob = fetchEmojis()
                    fetchEmojisJob.join()
                    pickRandomEmoji()
                }
            }
            is MainScreenActions.SearchAvatar -> {
                searchAvatar(viewActions.username)
            }
        }
    }

    fun fetchEmojis() : Job{
        _uiState.update {
            it.copy(isLoading = true)
        }

        return viewModelScope.launch(Dispatchers.IO) {
            cachedEmojiList = gitHubRepository.getEmojiList()
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false
                )
            }
        }
    }

    fun pickRandomEmoji() {
        if(cachedEmojiList.isNotEmpty()) {
            _uiState.update {
                it.copy (imageUrl = cachedEmojiList.random().url)
            }
        }
    }

    fun searchAvatar(username: String) {
        _uiState.update {
            it.copy(isSearching = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val avatar = gitHubRepository.findAvatar(username)
                _uiState.update {
                    it.copy(imageUrl = avatar.avatarUrl)
                }
            }
            catch (e: Exception) {
                _snackBarEvent.send(e.message ?: "Unknown error")
            }
            finally {
                _uiState.update {
                    it.copy(isSearching = false)
                }
            }
        }
    }
}