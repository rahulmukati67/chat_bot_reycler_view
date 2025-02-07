package com.example.chat_bot_reycler_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot_recyclerview.ChatBotRecyclerView
import com.example.chat_bot_recyclerview.model.ChatMessage
import com.example.chat_bot_recyclerview.model.ChatbotData
import com.example.chat_bot_recyclerview.model.Message
import com.example.chat_bot_recyclerview.model.SampleQuestions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val chatBotRecyclerView = findViewById<ChatBotRecyclerView>(R.id.chat_bot_recyclerview)
        val sampleQuestions = SampleQuestions(
            listOf(
                ChatbotData(
                    question = "What is AI?",
                    answer = Message(
                        text = "AI, or Artificial Intelligence, is the simulation of human intelligence processes by machines, especially computer systems.",
                        related_questions = SampleQuestions(
                            chatbotData = listOf(
                                ChatbotData(
                                    question = "Can you name some AI technologies?",
                                    answer = Message(
                                        text = "Sure, some AI technologies include machine learning, natural language processing, and robotics.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                ),
                                ChatbotData(
                                    question = "What are the benefits of AI?",
                                    answer = Message(
                                        text = "AI can help in various fields such as healthcare, finance, and customer service, improving efficiencies and creating new opportunities.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                )
                            )
                        )
                    )
                ),

                ChatbotData(
                    question = "What are you?",
                    answer = Message(
                        text = "AI, or Artificial Intelligence",
                        related_questions = SampleQuestions(
                            chatbotData = listOf(
                                ChatbotData(
                                    question = "How can I help you ",
                                    answer = Message(
                                        text = "Sure,natural language processing, and robotics.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                ),
                                ChatbotData(
                                    question = "What ",
                                    answer = Message(
                                        text = "AI",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                )
                            )
                        )
                    )
                ),
            )
        )
        chatBotRecyclerView.addSampleQuestions(sampleQuestions)

        chatBotRecyclerView.setChatThemeColors(
//            sendButtonColor = R.color.black,
//            recievedMessageTextColor= com.example.chat_bot_recyclerview.R.color.black
        )

//
//        chatBotRecyclerView.setOnMessageReceivedListener(object :
//            ChatBotRecyclerView.OnMessageReceivedListener {
//            override fun onMessageSent(message: String) {
//                chatBotRecyclerView.receiveMessage("Main Activity: $message")
//
//            }
//        })

        chatBotRecyclerView.setThumbsActionListener(object :
            ChatBotRecyclerView.ThumbsActionListener {
            override fun onThumbsUpClicked(message: ChatMessage) {
                Toast.makeText(this@MainActivity, "Liked", Toast.LENGTH_SHORT).show()
            }

            override fun onThumbsDownClicked(message: ChatMessage) {
                Toast.makeText(this@MainActivity, "Disliked:", Toast.LENGTH_SHORT).show()
            }
        })

    }
}