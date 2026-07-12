package pt.viabilize.blissandroidchallenge.ui.avatarlistscreen

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
import pt.viabilize.blissandroidchallenge.model.Avatar
import pt.viabilize.blissandroidchallenge.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class AvatarListViewModel @Inject constructor(
    val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AvatarListViewState())
    val uiState: StateFlow<AvatarListViewState> = _uiState.onStart {
        loadAvatars()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private fun loadAvatars() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    avatarList = gitHubRepository.getAvatarList()
                )
            }
        }
    }

    fun removeFromList(avatar: Avatar) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            gitHubRepository.removeAvatar(avatar)
            _uiState.update {
                it.copy(
                    avatarList = gitHubRepository.getAvatarList(),
                    isLoading = false
                )
            }
        }
    }
}