package com.example.restapitest

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_category.*

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.slide_in,R.anim.silde_out)
        category1.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            intent.putExtra("category","3")
            intent.putExtra("name",category1.text)
            startActivity(intent,options.toBundle())
        }
        category2.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            intent.putExtra("category","4")
            intent.putExtra("name",category2.text)
            startActivity(intent,options.toBundle())
        }
        category3.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            intent.putExtra("category",category3.id)
            intent.putExtra("name",category3.text)
            startActivity(intent,options.toBundle())
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }
}