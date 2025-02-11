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
) : LinearLayout(context, attrs, defStyleAttr) {

    private val recyclerView: RecyclerView
    private val editTextMessage: EditText
    private val buttonSend: ImageButton
    private val btnMic: ImageButton
    private val chatAdapter: ChatAdapter
    private val chatMessages: MutableList<ChatMessage> = mutableListOf()


    interface ThumbsActionListener {
        fun onThumbsUpClicked(message: ChatMessage)
        fun onThumbsDownClicked(message: ChatMessage)
    }

    /**
     * Interface for receiving message send events.
     */
    interface OnMessageReceivedListener {
        fun onMessageSent(message: String)
    }

    /** Interface for receiving mic events.*/
    interface OnMicClickListener {
        fun onMicClicked()
    }

    // Listener for handling received messages
    private var messageReceivedListener: OnMessageReceivedListener? = null
    private var micActionListener: OnMicClickListener? = null
    private var thumbsActionListener: ThumbsActionListener? = null


    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_chat_bot_recyclerview, this, true)

        recyclerView = findViewById(R.id.rvChatMessage)
        editTextMessage = findViewById(R.id.etMessage)
        buttonSend = findViewById(R.id.btnSendMessage)
        btnMic = findViewById(R.id.btnMic)

        chatAdapter = ChatAdapter(chatMessages, null)
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = chatAdapter

        // Handle send button click
        buttonSend.setOnClickListener {
            sendMessage(null)
        }
        btnMic.setOnClickListener {
            micActionListener?.onMicClicked()
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
                }, 0) //  delay
            }
        })

//        setOnMicClickListener(object : OnMicClickListener {
//            override fun onMicClicked() {
//
//            }
//        })


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
        }, 3000)


    }


    /**
     * Adds a set of predefined questions and answers.
     * @param sampleQuestions The sample questions to add.
     */
    fun addSampleQuestions(sampleQuestions: SampleQuestions) {
        if (sampleQuestions.chatbotData.isEmpty()) {
            findViewById<RecyclerView>(R.id.rvPredefinedQuestion).visibility = View.GONE
            return
        }

        val predefinedAdapter = PredefinedQuestionsAdapter(sampleQuestions) { selectedQuestion ->
            handleSelectedQuestion(selectedQuestion, sampleQuestions)
        }

        setupRecyclerView(R.id.rvPredefinedQuestion, predefinedAdapter)
    }

    private fun handleSelectedQuestion(selectedQuestion: String, sampleQuestions: SampleQuestions) {
        sendMessage(selectedQuestion)
        val answer = sampleQuestions.chatbotData.find { it.question == selectedQuestion }?.answer
        answer?.text?.let { receiveMessage(it) }

        // Update or hide the RecyclerView based on the presence of related questions
        if (answer?.related_questions?.chatbotData?.isNotEmpty() == true) {
            updateRelatedQuestionsRecyclerView(answer.related_questions)
        } else {
            findViewById<RecyclerView>(R.id.rvRelatedQuestions).visibility = View.GONE
        }
    }

    private fun updateRelatedQuestionsRecyclerView(relatedQuestions: SampleQuestions) {
        if (relatedQuestions.chatbotData.isEmpty()) {
            findViewById<RecyclerView>(R.id.rvRelatedQuestions).visibility = View.GONE
        } else {
            val predefinedAdapter =
                PredefinedQuestionsAdapter(relatedQuestions) { selectedQuestion ->
                    sendMessage(selectedQuestion)
                    receiveMessage(relatedQuestions.chatbotData.find { it.question == selectedQuestion }?.answer?.text.orEmpty())
                    findViewById<RecyclerView>(R.id.rvRelatedQuestions).visibility = View.GONE
                }
            setupRecyclerView(R.id.rvRelatedQuestions, predefinedAdapter)
        }
    }


    private fun setupRecyclerView(recyclerViewId: Int, adapter: RecyclerView.Adapter<*>) {
        findViewById<RecyclerView>(recyclerViewId).apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }


    /**
     * Sets a listener to handle message sending events.
     * @param listener The listener to set.
     */
    private fun setOnMessageReceivedListener(listener: OnMessageReceivedListener) {
        this.messageReceivedListener = listener
    }

    /**
     * Sets a listener to handle message sending events.
     * @param listener The listener to set.
     */
    fun setOnMicClickListener(listener: OnMicClickListener) {
        this.micActionListener = listener
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


    fun setChatThemeColors(
        backgroundColor: Int? = null,
        sendButtonColor: Int? = null,
        receivedMessageTextColor: Int? = null
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
        receivedMessageTextColor?.let {
            chatAdapter.setReceivedMessageTextColor(receivedMessageTextColor)
        }
    }

    fun setThumbsActionListener(listener: ThumbsActionListener) {
        this.thumbsActionListener = listener
        chatAdapter.thumbsActionListener =
            listener  // Ensure the adapter receives the updated listener

    }

}
