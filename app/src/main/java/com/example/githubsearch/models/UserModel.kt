package com.example.githubsearch.models

data class UserModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)