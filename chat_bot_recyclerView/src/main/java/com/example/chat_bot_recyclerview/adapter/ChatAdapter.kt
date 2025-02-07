package com.example.chat_bot_recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot_recyclerview.R
import com.example.chat_bot_recyclerview.model.ChatMessage


class ChatAdapter(
    private val chatMessages: MutableList<ChatMessage>, private val callback: ChatAdapterCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var receivedMessageTextColor: Int? = null

    fun setReceivedMessageTextColor(color: Int) {
        receivedMessageTextColor = color
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (chatMessages[position].type) {
            ChatMessage.Type.SENT -> VIEW_TYPE_SENT
            ChatMessage.Type.RECEIVED -> VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_chat_item_sent, parent, false)
                SentMessageViewHolder(view)
            }

            VIEW_TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_chat_item_received, parent, false)
                ReceivedMessageViewHolder(view, callback)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = chatMessages[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message, receivedMessageTextColor)
        }
    }

    override fun getItemCount(): Int = chatMessages.size

    fun addMessage(message: ChatMessage) {
        chatMessages.add(message)
        notifyItemInserted(chatMessages.size - 1)
    }

    // ViewHolder for Sent Messages
    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageBody: TextView = itemView.findViewById(R.id.tvMessageSent)

        fun bind(message: ChatMessage) {
            messageBody.text = message.message
        }
    }

    // ViewHolder for Received Messages
    class ReceivedMessageViewHolder(itemView: View, private val callback: ChatAdapterCallback) :
        RecyclerView.ViewHolder(itemView) {
        private val messageBody: TextView = itemView.findViewById(R.id.tvMessageReceived)
        private val thumbsUp: ImageView = itemView.findViewById(R.id.imgThumbsUp)
        private val thumbsDown: ImageView = itemView.findViewById(R.id.imgThumbsDown)


        fun bind(message: ChatMessage, textColor: Int?) {
            messageBody.text = message.message
            textColor?.let { messageBody.setTextColor(it) } // Apply received message text color

            // Set click listeners for thumbs up/down
            thumbsUp.setOnClickListener {
                thumbsUp.setColorFilter(ContextCompat.getColor(itemView.context, R.color.green))
                thumbsDown.clearColorFilter()
                callback.onThumbsUpClicked(adapterPosition, message)
            }

            thumbsDown.setOnClickListener {
                thumbsDown.setColorFilter(ContextCompat.getColor(itemView.context, R.color.red))
                thumbsUp.clearColorFilter()
                callback.onThumbsDownClicked(adapterPosition, message)
            }

        }
    }

    interface ChatAdapterCallback {
        fun onThumbsUpClicked(position: Int, message: ChatMessage)
        fun onThumbsDownClicked(position: Int, message: ChatMessage)
    }


}
