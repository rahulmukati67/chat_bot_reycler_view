package com.example.chat_bot_recyclerview.model

data class ChatMessage(
    val message: String,
    val type: Type,
    val timestamp: Long = System.currentTimeMillis()
) {
    enum class Type {
        SENT,
        RECEIVED
    }
}
