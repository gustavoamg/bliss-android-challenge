package pt.blissapps.blissandroidchallenge.ui.emojilistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.blissapps.blissandroidchallenge.model.Emoji
import pt.blissapps.blissandroidchallenge.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(
    val gitHubRepository: GitHubRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmojiListScreenState())
    private var emojiList = emptyList<Emoji>()

    val uiState: StateFlow<EmojiListScreenState> = _uiState.onStart {
            loadEmojis()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    fun loadEmojis() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            emojiList = gitHubRepository.getEmojiList()
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    emojiList = emojiList
                )
            }
        }
    }

    fun removeFromList(emoji: Emoji) {
        emojiList = emojiList.minusElement(emoji)
        _uiState.update {
            it.copy(
                emojiList = emojiList
            )
        }
    }
}