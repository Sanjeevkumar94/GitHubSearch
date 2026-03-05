package com.example.githubsearch.repository

import com.example.githubsearch.api.GitHubApi
import com.example.githubsearch.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubApi: GitHubApi
) {

    private val _usersList = MutableStateFlow<List<Item>>(emptyList())

    val usersList: StateFlow<List<Item>> = _usersList

    private var _isLoading = MutableStateFlow(false)
     var isLoading = _isLoading





    suspend fun getGitHubUserList(q: String) {
        _isLoading.value = true
        val response = gitHubApi.getGitHubUsersList(q)
        _isLoading.value = false

        if(response.isSuccessful && response.body() !=null){
            response.body()?.let {
                _usersList.emit(it.items)
            }
        }
    }

    fun clearUsers(){
        _usersList.value = emptyList()
    }
}

