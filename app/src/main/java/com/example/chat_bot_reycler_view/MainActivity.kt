package com.example.chat_bot_reycler_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot_recyclerview.ChatBotRecyclerView
import com.example.chat_bot_recyclerview.adapter.PredefinedQuestionsAdapter
import com.example.chat_bot_recyclerview.model.ChatbotData
import com.example.chat_bot_recyclerview.model.SampleQuestions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val chatBotRecyclerView = findViewById<ChatBotRecyclerView>(R.id.chat_bot_recyclerview)
        val sampleQuestions = SampleQuestions(
            chatbotData = listOf(
                ChatbotData("Hello! How can I help you?", "Hi"),
                ChatbotData("I'm a chatbot created for demonstration purposes.", "What are you?"),
                ChatbotData("Sure, I can help with that!", "Can you assist me?")
            )
        )

        chatBotRecyclerView.addSampleQuestions(sampleQuestions)

//        chatBotRecyclerView.setOnMessageReceivedListener(object : ChatBotRecyclerView.OnMessageReceivedListener {
//            override fun onMessageSent(message: String) {
//                // Handle message sent, e.g., send to chatbot API and receive response
//                // For demonstration, we have an echo response
//                // To customize, override the default behavior or set a different listener
//            }
//        })
    }
}