package com.example.githubsearch

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubsearch.ui.theme.GitHubSearchTheme
import com.example.githubsearch.viewmodels.GitHubUserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val gitHubViewModel: GitHubUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubSearchTheme {
                var query by rememberSaveable { mutableStateOf("") }
                val usersList by gitHubViewModel.userList.collectAsState()
                val isLoading by gitHubViewModel.isLoading.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(Modifier.padding(innerPadding)) {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().padding(18.dp),
                            value = query,
                            onValueChange = {
                                query = it
                                if(it.isNotBlank()){
                                    gitHubViewModel.searchUsers(it)
                                }

                            },
                            shape = RoundedCornerShape(18.dp),
                            placeholder = { Text("Search User")}

                        )

                        Box(modifier = Modifier.fillMaxSize()){
                            LazyColumn() {

                                items(usersList) {
                                    Column(modifier = Modifier.fillMaxWidth()) {

                                        AsyncImage(
                                            model = it.avatar_url,
                                            contentDescription = "",
                                            modifier = Modifier.size(120.dp)
                                        )
                                    }
                                }

                            }

                            if(isLoading){
                                CircularProgressIndicator()
                            }
                        }


                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GitHubSearchTheme {
//        Greeting("Android")
//    }
//}