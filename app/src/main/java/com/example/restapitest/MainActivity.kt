package com.example.restapitest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    var tv1: TextView? = null
    var locate:Int=0
    var total:Int=0
    var templist=ArrayList<Quiz>()
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1=findViewById(R.id.text_test1)
        val nb = findViewById<Button>(R.id.next_button)
        // 1. JSON 파일 열어서 String으로 취득
        val jsonString = assets.open("quiz.json").reader().readText()
        Log.d("jsonString", jsonString)

        // 2. JSONArray 로 파싱
        val jsonArray = JSONArray(jsonString)
        Log.d("jsonArray", jsonArray.toString())

        // 3. JSONArray 순회: 인덱스별 JsonObject 취득후, key에 해당하는 value 확인
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)

            val id = jsonObject.getString("title")
            val body = jsonObject.getString("body")
            val test=jsonObject.getString("answer").toInt()
            total++
            templist.add(Quiz(id,body,test))



        }
        tv1?.setText(templist[0].title)
        nb.setOnClickListener {
            locate++
            if (locate < 3){
                tv1?.setText(templist[locate].title)
            }
        }

    }



}