package com.example.githubsearch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.models.Item
import com.example.githubsearch.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GitHubUserViewModel @Inject constructor(private val gitHubRepository: GitHubRepository) :
    ViewModel() {

    val userList: StateFlow<List<Item>>  = gitHubRepository.usersList

    val query = MutableStateFlow("")
    val isLoading = gitHubRepository.isLoading



    init {
    /*    viewModelScope.launch {

            query
                .debounce(1000)
                .distinctUntilChanged()
                .collectLatest { q ->

                    if (q.isBlank()) {
                        gitHubRepository.clearUsers()
                    } else {
                        hitApi(q)
                    }

                }
        }*/

        viewModelScope.launch {
            query
                .debounce(1000)
                .distinctUntilChanged()
//                .filter { it.isNotBlank() }
                .collectLatest { q ->
                    if (q.isBlank()) {
                        gitHubRepository.clearUsers()
                    } else {
                        runCatching { hitApi(q) }
                            .onFailure { /* handle error */ }
                    }
                }
        }

    }


  /*  filter → removes empty queries
    distinctUntilChanged → prevents duplicate queries
    debounce → waits for user to stop typing
    collectLatest → cancels previous API calls*/

   suspend fun hitApi(q: String){
       gitHubRepository.getGitHubUserList(q)
    }

    fun searchUsers(q: String) {
        query.value = q

    }

}