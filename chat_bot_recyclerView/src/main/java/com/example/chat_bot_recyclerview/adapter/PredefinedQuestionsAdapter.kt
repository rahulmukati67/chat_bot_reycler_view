package com.example.chat_bot_recyclerview.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot_recyclerview.R
import com.example.chat_bot_recyclerview.model.SampleQuestions

class PredefinedQuestionsAdapter(
    private val questions: SampleQuestions,
    private val onQuestionClick: (String) -> Unit
) : RecyclerView.Adapter<PredefinedQuestionsAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_predefines_questions, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions.chatbotData[position].question
        holder.bind(question)
        holder.itemView.setOnClickListener {
            onQuestionClick(question)
        }
    }

    override fun getItemCount(): Int = questions.chatbotData.size

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)

        fun bind(question: String) {
            tvQuestion.text = question
        }
    }
}
