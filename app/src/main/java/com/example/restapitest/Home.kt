package com.example.restapitest

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Home : AppCompatActivity() {
    val quizbook= ArrayList<Quizbook>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.slide_in,R.anim.silde_out)
        val name= intent.getStringExtra("name")
        val temp=intent.getStringExtra("category")
        val url = URL("https://port-0-drf-quiz-6g2lleww4j2b.sel3.cloudtype.app/main/$temp")
        val url_temp="https://port-0-drf-quiz-6g2lleww4j2b.sel3.cloudtype.app/main/"
        val url_item="https://port-0-drf-quiz-6g2lleww4j2b.sel3.cloudtype.app/main/$temp"


        Thread{
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 데이터 읽기
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val builder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                reader.close()

                // JSON 파싱하기
                val jsonArray = JSONArray(builder.toString())
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val quizName = jsonObject.getString("quiz_name")
                    val quizId = jsonObject.getInt("quiz_id")
                    quizbook.add(Quizbook(quizId,quizName))
                    Log.e("test123123123",quizbook[i].quizId.toString())
                }
                Log.e("과녕?",quizbook.toString())

                runOnUiThread{
                    // 필요한 데이터 추출하기
                    textView.setText(jsonArray.toString())
                    val adapter = QuizbookAdapter(quizbook) { quiz ->
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("quizId", quiz.quizId.toString())
                        intent.putExtra("quizName", quiz.quizName)
                        intent.putExtra("url",url_item)
                        intent.putExtra("name",name)
                        intent.putExtra("category",temp)
                        intent.putExtra("url_item",url_temp)
                        startActivity(intent)
                    }
                    quizbookview.adapter = adapter
                    quizbookview.layoutManager = LinearLayoutManager(this)
                    Log.e("testss",jsonArray.toString())
                }

            }

        }.start()

        category_name.setText(name)

        val intent = Intent(this,MainActivity::class.java)
        subject1.setOnClickListener {
            intent.putExtra("subject",subject1.id)
            startActivity(intent,options.toBundle())
        }
        subject2.setOnClickListener {
            intent.putExtra("subject",subject2.id)
            startActivity(intent,options.toBundle())
        }
        subject3.setOnClickListener {
            intent.putExtra("subject",subject3.id)
            startActivity(intent,options.toBundle())
        }
        val quizRecyclerView: RecyclerView = findViewById(R.id.quizbookview)



    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }
}