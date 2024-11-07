package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() { //här skapas huvudaktiviteten för appen

    private var currentQuestionIndex = 0 //håller koll på vilken fråga vi är på just nu
    private var score = 0 //sparar antalet rätt svar

    private val questions = listOf( //lista med frågor
        Question("What is 2 + 2?", "3", "4", "5", "4"),
        Question("What is 3 + 3?", "6", "5", "8", "6"),
        Question("What is 5 + 7?", "12", "10", "11", "12")
    )

    override fun onCreate(savedInstanceState: Bundle?) { //vad som ska ske när appen startas
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //här laddas layouten för main

        val questionText = findViewById<TextView>(R.id.question_text)
        val answerButton1 = findViewById<Button>(R.id.answer_button_1)
        val answerButton2 = findViewById<Button>(R.id.answer_button_2)
        val answerButton3 = findViewById<Button>(R.id.answer_button_3)
        val scoreText = findViewById<TextView>(R.id.score_text)
        val resultText = findViewById<TextView>(R.id.result_text)

        // Set initial question
        updateQuestion()

        // Define the click listeners
        answerButton1.setOnClickListener {
            checkAnswer(answerButton1, answerButton1.text.toString())
        }

        answerButton2.setOnClickListener {
            checkAnswer(answerButton2, answerButton2.text.toString())
        }

        answerButton3.setOnClickListener {
            checkAnswer(answerButton3, answerButton3.text.toString())
        }

        // Update score text
        scoreText.text = "Score: $score"
    }

    private fun checkAnswer(selectedButton: Button, selectedAnswer: String) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer

        // Change the color of the selected button based on the answer
        if (selectedAnswer == correctAnswer) {
            score++
            selectedButton.setBackgroundColor(resources.getColor(R.color.correctAnswerColor))
        } else {
            selectedButton.setBackgroundColor(resources.getColor(R.color.incorrectAnswerColor))
        }

        // Move to the next question automatically after 1 second
        Handler().postDelayed({
            // Go to next question
            currentQuestionIndex++

            // If there are no more questions, show the result
            if (currentQuestionIndex >= questions.size) {
                showResult()
            } else {
                // Otherwise update the question and reset button colors
                updateQuestion()
            }
        }, 1000) // Delay for 1 second to show the result before moving to the next question
    }

    private fun updateQuestion() {
        // Reset button colors to the default (no selection)
        val answerButton1 = findViewById<Button>(R.id.answer_button_1)
        val answerButton2 = findViewById<Button>(R.id.answer_button_2)
        val answerButton3 = findViewById<Button>(R.id.answer_button_3)

        answerButton1.setBackgroundColor(resources.getColor(R.color.defaultButtonColor))
        answerButton2.setBackgroundColor(resources.getColor(R.color.defaultButtonColor))
        answerButton3.setBackgroundColor(resources.getColor(R.color.defaultButtonColor))

        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            findViewById<TextView>(R.id.question_text).text = question.text
            findViewById<Button>(R.id.answer_button_1).text = question.answer1
            findViewById<Button>(R.id.answer_button_2).text = question.answer2
            findViewById<Button>(R.id.answer_button_3).text = question.answer3
        }
    }

    private fun showResult() {
        findViewById<TextView>(R.id.score_text).visibility = View.GONE
        findViewById<TextView>(R.id.result_text).visibility = View.VISIBLE
        findViewById<TextView>(R.id.result_text).text = "You scored $score out of ${questions.size}."
    }
}

data class Question(val text: String, val answer1: String, val answer2: String, val answer3: String, val correctAnswer: String)