package io.payflow.android.model

data class User(
    val id: String,
    val displayName: String,
    val email: String,
    val photoUrl: String?
)