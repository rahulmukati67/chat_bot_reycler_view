package com.example.chat_bot_recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
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
) : LinearLayout(context, attrs, defStyleAttr) {

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

        chatAdapter = ChatAdapter(chatMessages)
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

////         Handle custom attributes
//        attrs?.let {
//            val typedArray = context.obtainStyledAttributes(it, R.styleable.ChatBotRecyclerView, 0, 0)
//            val sendButtonText = typedArray.getString(R.styleable.ChatBotRecyclerView_sendButtonText)
//            val inputHint = typedArray.getString(R.styleable.ChatBotRecyclerView_inputHint)
//            sendButtonText?.let { text -> buttonSend.text = text }
//            inputHint?.let { hint -> editTextMessage.hint = hint }
//            typedArray.recycle()
//        }

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
        chatAdapter.addMessage(message)
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
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
                    sendMessage("User: $selectedQuestion")
                    receiveMessage("Bot: ${sampleQuestions.chatbotData.find { it.question == selectedQuestion }?.answer}")
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
}
