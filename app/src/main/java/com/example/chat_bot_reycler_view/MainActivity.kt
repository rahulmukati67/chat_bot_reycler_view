package com.example.chat_bot_reycler_view

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.chat_bot_recyclerview.ChatBotRecyclerView
import com.example.chat_bot_recyclerview.model.ChatMessage
import com.example.chat_bot_recyclerview.model.ChatbotData
import com.example.chat_bot_recyclerview.model.Message
import com.example.chat_bot_recyclerview.model.SampleQuestions
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var outputText: String
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private lateinit var chatBotRecyclerView: ChatBotRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        chatBotRecyclerView = findViewById(R.id.chat_bot_recyclerview)
        val sampleQuestions = SampleQuestions(
            listOf(
                ChatbotData(
                    question = "What is AI?", answer = Message(
                        text = "AI, or Artificial Intelligence, is the simulation of human intelligence processes by machines, especially computer systems.",
                        related_questions = SampleQuestions(
                            chatbotData = listOf(
                                ChatbotData(
                                    question = "Can you name some AI technologies?",
                                    answer = Message(
                                        text = "Sure, some AI technologies include machine learning, natural language processing, and robotics.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                ), ChatbotData(
                                    question = "What are the benefits of AI?", answer = Message(
                                        text = "AI can help in various fields such as healthcare, finance, and customer service, improving efficiencies and creating new opportunities.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                )
                            )
                        )
                    )
                ),

                ChatbotData(
                    question = "What are you?", answer = Message(
                        text = "AI, or Artificial Intelligence",
                        related_questions = SampleQuestions(
                            chatbotData = listOf(
                                ChatbotData(
                                    question = "How can I help you ", answer = Message(
                                        text = "Sure,natural language processing, and robotics.",
                                        related_questions = SampleQuestions(emptyList())
                                    )
                                ), ChatbotData(
                                    question = "What ", answer = Message(
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
//            receivedMessageTextColor= com.example.chat_bot_recyclerview.R.color.black
        )

        chatBotRecyclerView.setOnMicClickListener(object : ChatBotRecyclerView.OnMicClickListener {
            override fun onMicClicked() {
                captureVoice()
            }
        })

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


    fun captureVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

        try {
            ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SPEECH_INPUT, null)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // onActivityResult to capture voice input
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                outputText = res[0] // Capture the first result as outputText
                chatBotRecyclerView.sendMessage("Voice Text: $outputText")
                chatBotRecyclerView.receiveMessage(outputText)
            }
        }
    }
}