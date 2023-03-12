package com.example.restapitest

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class QuizbookAdapter(val quizbooklist:List<Quizbook>,private val onItemClick: (Quizbook)-> Unit) :
    RecyclerView.Adapter<QuizbookAdapter.QuizbookViewHolder>(){

    inner class QuizbookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quizNameView: TextView = itemView.findViewById(R.id.quizbooklistitem)

        fun bind(quizbook: Quizbook) {
            quizNameView.text = quizbook.quizName
            Log.e("어뎁터 테스트",quizbook.quizName)
            itemView.setOnClickListener { onItemClick(quizbook) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizbookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quizbook_item, parent, false)
        return QuizbookViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizbookViewHolder, position: Int) {
        holder.bind(quizbooklist[position])
    }

    override fun getItemCount(): Int = quizbooklist.size

}