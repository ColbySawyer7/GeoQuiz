package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private lateinit var questionTextView: TextView


    private val questionBank = listOf(Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private val answeredBank = MutableList(questionBank.size, { false })

    private var currentIndex = 0
    private var numAnswered = 0
    private var ansCorrect = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{
            view: View ->
            // Do something in response to the click here
            checkAnswer(true)
            updateUI()
        }
        falseButton.setOnClickListener {
                view: View ->
            // Do something in response to the click here
            checkAnswer(false)
            updateUI()
        }
        nextButton.setOnClickListener{
            //Do something in response to the click here
            currentIndex = (currentIndex + 1) % questionBank.size
            updateUI()

        }
        updateUI()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateUI(){
        trueButton.isEnabled = !answeredBank[currentIndex]
        falseButton.isEnabled = !answeredBank[currentIndex]

        updateQuestion()
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if(userAnswer == correctAnswer){
            ++ansCorrect
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        answeredBank[currentIndex] = true
        ++numAnswered

        if(numAnswered == questionBank.size){
            var percentCorrect = ansCorrect * 100.0 / questionBank.size
            Toast.makeText(this, "You scored ${ansCorrect} our " +
                    "of ${questionBank.size} for a score of ${percentCorrect}%",
                Toast.LENGTH_SHORT).show()
        }
    }
}
