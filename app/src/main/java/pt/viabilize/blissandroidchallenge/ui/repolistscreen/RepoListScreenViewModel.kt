package pt.viabilize.blissandroidchallenge.ui.repolistscreen

import android.util.Log
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
import pt.viabilize.blissandroidchallenge.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class RepoListScreenViewModel @Inject constructor(
    val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val username = "google"
    private var reposPerPage = 20
    private var currentPage = 1
    private var isLastPageReached = false
    private var isPageLoading = false

    private val _uiState = MutableStateFlow(RepoListScreenState())
    val uiState: StateFlow<RepoListScreenState> = _uiState.onStart {
        loadNextPage()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    fun loadNextPage() {
        try {
            if (isPageLoading || isLastPageReached) return

            isPageLoading = true

            _uiState.update {
                it.copy(isLoading = true)
            }

            viewModelScope.launch(Dispatchers.IO) {
                val currentPageList = gitHubRepository.loadRepos(username, currentPage, reposPerPage)

                if (currentPageList.isEmpty() || currentPageList.size < reposPerPage ) {
                    isLastPageReached = true
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        repoList = currentState.repoList + currentPageList
                    )
                }

                if(!isLastPageReached) {
                    currentPage++
                }
            }
        }
        catch (e: Exception) {
            Log.e("LOADING_REPO_ERROR", e.message ?: "Unknown error")
            _uiState.update { it.copy(isLoading = false ) }
        } finally {
            isPageLoading = false
        }
    }
}