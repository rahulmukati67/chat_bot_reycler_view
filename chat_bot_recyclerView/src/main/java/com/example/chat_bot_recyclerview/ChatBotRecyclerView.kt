package com.example.chat_bot_recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot_recyclerview.adapter.ChatAdapter
import com.example.chat_bot_recyclerview.adapter.PredefinedQuestionsAdapter
import com.example.chat_bot_recyclerview.model.ChatMessage
import com.example.chat_bot_recyclerview.model.SampleQuestions

class ChatBotRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ChatAdapter.ChatAdapterCallback {

    private val recyclerView: RecyclerView
    private val editTextMessage: EditText
    private val buttonSend: ImageButton
    private val chatAdapter: ChatAdapter
    private val chatMessages: MutableList<ChatMessage> = mutableListOf()


    // Listener for handling received messages
    private var messageReceivedListener: OnMessageReceivedListener? = null

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_chat_bot_recyclerview, this, true)

        recyclerView = findViewById(R.id.rvChatMessage)
        editTextMessage = findViewById(R.id.etMessage)
        buttonSend = findViewById(R.id.btnSendMessage)

        chatAdapter = ChatAdapter(chatMessages, this)
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = chatAdapter

        // Handle send button click
        buttonSend.setOnClickListener {
            sendMessage(null)
        }

        // Optional: Disable send button when input is empty
        editTextMessage.doAfterTextChanged { text ->
            buttonSend.isEnabled = !text.isNullOrBlank()
        }


        // Set a default message received listener (simple echo)
        setOnMessageReceivedListener(object : OnMessageReceivedListener {
            override fun onMessageSent(message: String) {
                // Simulate a response after a delay
                recyclerView.postDelayed({
                    val response = generateResponse(message)
                    receiveMessage(response)
                }, 1000) // 1-second delay
            }
        })
    }

    /**
     * Sends a message typed by the user.
     */
    fun sendMessage(messageText: String?) {

        val messageTextFromTextView = editTextMessage.text.toString().trim()
        if (messageTextFromTextView.isNotEmpty()) {
            val message = ChatMessage(messageTextFromTextView, ChatMessage.Type.SENT)
            chatAdapter.addMessage(message)
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            editTextMessage.text.clear()

            // Notify listener for response
            messageReceivedListener?.onMessageSent(messageTextFromTextView)
        } else if (messageText != null) {
            if (messageText.isNotEmpty()) {
                val message = ChatMessage(messageText, ChatMessage.Type.SENT)
                chatAdapter.addMessage(message)
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                findViewById<NestedScrollView>(R.id.rvParentNestedScrollView).post {
                    findViewById<NestedScrollView>(R.id.rvParentNestedScrollView).scrollTo(
                        0,
                        recyclerView.bottom
                    )
                }
                editTextMessage.text.clear()
            }
        }
    }

    /**
     * Adds a received message to the chat.
     * @param messageText The message text.
     */
    fun receiveMessage(messageText: String) {
        val message = ChatMessage(messageText, ChatMessage.Type.RECEIVED)
        val handler = android.os.Handler(Looper.getMainLooper())

        findViewById<ProgressBar>(R.id.dot_progress_loader).visibility = View.VISIBLE
        handler.postDelayed({
            chatAdapter.addMessage(message)
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            findViewById<NestedScrollView>(R.id.rvParentNestedScrollView).post {
                findViewById<NestedScrollView>(R.id.rvParentNestedScrollView).scrollTo(
                    0,
                    recyclerView.bottom
                )
                findViewById<ProgressBar>(R.id.dot_progress_loader).visibility = View.GONE
            }
        }, 2000)


    }


    /**
     * Adds a set of predefined questions and answers.
     * @param sampleQuestions The sample questions to add.
     */
    fun addSampleQuestions(sampleQuestions: SampleQuestions) {

        if (sampleQuestions.chatbotData.isNotEmpty()) {
            val predefinedAdapter =
                PredefinedQuestionsAdapter(sampleQuestions) { selectedQuestion ->
                    // Handle question click
                    sendMessage(selectedQuestion)
                    receiveMessage("${sampleQuestions.chatbotData.find { it.question == selectedQuestion }?.answer}")
                }

            val rvPredefinedQuestions = findViewById<RecyclerView>(R.id.rvPredefinedQuestion)

            rvPredefinedQuestions.layoutManager = LinearLayoutManager(context)
            rvPredefinedQuestions.adapter = predefinedAdapter
        }
    }

    /**
     * Sets a listener to handle message sending events.
     * @param listener The listener to set.
     */
    fun setOnMessageReceivedListener(listener: OnMessageReceivedListener) {
        this.messageReceivedListener = listener
    }

    /**
     * Generates a simple echo response.
     * @param message The received message.
     * @return The response message.
     */
    private fun generateResponse(message: String): String {
        // For demonstration, simply echo the message
        return "Echo: $message"
    }

    /**
     * Interface for receiving message send events.
     */
    interface OnMessageReceivedListener {
        fun onMessageSent(message: String)
    }

    fun setChatThemeColors(
        backgroundColor: Int? = null,
        sendButtonColor: Int? = null,
        recievedMessageTextColor: Int? = null
    ) {
        backgroundColor?.let {
            findViewById<NestedScrollView>(R.id.rvParentNestedScrollView).setBackgroundColor(
                backgroundColor
            )
        }
        sendButtonColor?.let {
            findViewById<ImageButton>(R.id.btnSendMessage).backgroundTintList =
                ColorStateList.valueOf(sendButtonColor)
        }
        recievedMessageTextColor?.let {
            chatAdapter.setReceivedMessageTextColor(recievedMessageTextColor)
        }
    }


    override fun onThumbsUpClicked(position: Int, message: ChatMessage) {

    }

    override fun onThumbsDownClicked(position: Int, message: ChatMessage) {
    }
}
