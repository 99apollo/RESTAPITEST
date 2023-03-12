package com.example.restapitest

import android.app.ActivityOptions
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*
import com.example.restapitest.Category as Category1

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent_main=Intent(this,MainActivity::class.java)
        val intent_sub=Intent(this,Home::class.java)
        val intent_home=Intent(this,Category1::class.java)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.silde_out,R.anim.silde_out)
        val score=intent.getStringExtra("score")
        val quizid=intent.getStringExtra("quizId")
        val quizname=intent.getStringExtra("quizName")
        val temp_url=intent.getStringExtra("url")
        val urlitem=intent.getStringExtra("url_item")
        val category_item=intent.getStringExtra("category")
        val name_item=intent.getStringExtra("name")
        Log.e("넘어온 데이터 확인(결과창)",temp_url.toString())
        result.text = score.toString()
        restart.setOnClickListener {
            intent_main.putExtra("quizId", quizid)
            intent_main.putExtra("quizName", quizname)
            intent_main.putExtra("url",temp_url)
            startActivity(intent_main,options.toBundle())
            finish()
        }
        tohome.setOnClickListener {


            intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val stack = TaskStackBuilder.create(this)

            stack.addNextIntentWithParentStack(intent_home)
            stack.startActivities(options.toBundle())
        }
        tosub.setOnClickListener {
            intent_sub.putExtra("name",name_item)
            intent_sub.putExtra("category",category_item)
            startActivity(intent_sub)
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }
}