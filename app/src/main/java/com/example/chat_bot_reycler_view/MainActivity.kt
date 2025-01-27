package com.example.chat_bot_reycler_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot_recyclerview.ChatBotRecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val chatBotRecyclerView = findViewById<ChatBotRecyclerView>(R.id.chat_bot_recyclerview)


    }
}