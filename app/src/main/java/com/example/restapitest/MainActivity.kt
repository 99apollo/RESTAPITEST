package com.example.restapitest

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    var tv1: TextView? = null
    var tv2: TextView? = null
    var tv3: TextView? = null
    var locate:Int=0
    var total:Int=0
    var score:Int=0
    var end_check:Int=0
    var templist=ArrayList<Quiz>()
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1=findViewById(R.id.text_test1)
        tv2=findViewById(R.id.quiz_body)
        tv3=findViewById(R.id.text2)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val nb = findViewById<Button>(R.id.next_button)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.slide_in,R.anim.silde_out)

        val test_value=findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        val quizid=intent.getStringExtra("quizId")
        val quizname=intent.getStringExtra("quizName")
        val temp_url=intent.getStringExtra("url")
        val result_url=intent.getStringExtra("url_item")
        val category_item=intent.getStringExtra("category")
        val name_item=intent.getStringExtra("name")
        val url = URL("$temp_url/$quizid")
        Log.e("????????? ????????? ??????(??????)",quizid.toString())

        Thread{
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // ?????? ????????? ??????
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val builder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                reader.close()

                // JSON ????????????
                val jsonArray = JSONArray(builder.toString())
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("title")
                    val body = jsonObject.getString("body")
                    val answer = jsonObject.getString("answer").toInt()+1
                    total++
                    templist.add(Quiz(title,body,answer))
                    Log.e("?????? ??????",templist[i].title)
                }

                if(templist.isEmpty()){
                    Log.e("????????? ?????? ???????",templist.toString())
                    runOnUiThread {
                        Toast.makeText(this, "????????? ????????????!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else{
                    runOnUiThread{

                        tv1?.setText(templist[locate].title)
                        tv2?.setText(templist[locate].body)
                        nb.setOnClickListener {
                            if(nb.text=="????????????"){
                                locate=0
                                score=0
                                tv1?.setText(templist[locate].title)
                                tv2?.setText(templist[locate].body)
                                nb.setText("??????")
                            }else{

                                Log.e("?????? total ???",total.toString())
                                Log.e("?????? count ???",locate.toString())

                                val checkid=radioGroup.checkedRadioButtonId
                                val selectedRadioButton = findViewById<RadioButton>(checkid)



                                if (selectedRadioButton != null) {
                                    // ????????? ????????? ????????? ????????? ???????????????.
                                    val selectedRadioButtonText = selectedRadioButton.text// make sure a radio button is selected
                                    if (selectedRadioButtonText==(templist[locate].answer).toString()+"???  "){
                                        score++

                                    }else{
                                    }
                                    Log.d("MainActivity", "Selected radio button: $selectedRadioButtonText")
                                    Log.e("ckecktest",(templist[locate].answer).toString()+"???  ")
                                } else {
                                    Toast.makeText(this, "????????? ?????????!", Toast.LENGTH_SHORT).show()
                                    Log.d("MainActivity", "No radio button selected")
                                }
                                if(nb.text=="?????? ??????"){
                                    Log.e("???????????? ?????? ??????",score.toString())
                                    val intent=Intent(this,Result::class.java)
                                    intent.putExtra("score",score.toString())
                                    intent.putExtra("quizId",quizid)
                                    intent.putExtra("quizName",quizname)
                                    intent.putExtra("url",temp_url)
                                    intent.putExtra("url_item",result_url)
                                    intent.putExtra("category",category_item)
                                    intent.putExtra("name",name_item)
                                    startActivity(intent,options.toBundle())
                                    finish()
                                }
                                radioGroup.clearCheck()
                                locate++
                                if (locate < total){
                                    tv1?.setText(templist[locate].title)
                                    tv2?.setText(templist[locate].body)
                                    Log.e("body",templist[locate].title)
                                    if(locate+1==total){
                                        nb.setText("?????? ??????")
                                        end_check=1
                                    }
                                }else{
                                    radioGroup.clearCheck()
                                }
                            }

                        }
                        Log.e("testss",jsonArray.toString())
                    }
                }

            }

        }.start()












    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }

}

