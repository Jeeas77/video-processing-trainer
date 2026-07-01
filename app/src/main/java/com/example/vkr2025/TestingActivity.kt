package com.example.vkr2025

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.vkr2025.databinding.ActivityTestingBinding
import com.google.android.material.tabs.TabLayoutMediator
import android.content.DialogInterface

class TestingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: TestPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testTitle = intent.getStringExtra("testTitle") ?: "Основной тест"
        // Загружаем вопросы в зависимости от выбранного теста
        adapter = TestPagerAdapter(this, testTitle)

        // Инициализация ViewPager и адаптера

        viewPager = binding.viewPager
        viewPager.adapter = adapter

        // Связываем ViewPager с TabLayout
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = "Вопрос ${position + 1}"
        }.attach()
        // Кнопка завершения теста
        binding.btnFinishTest.setOnClickListener {
            calculateResults()
        }

        binding.btnFinishTest.setOnClickListener {
            if (allQuestionsAnswered()) {
                calculateResults()
            } else {
                showUnansweredQuestionsDialog()
            }
        }
    }
    private fun allQuestionsAnswered(): Boolean {
        for (i in 0 until adapter.itemCount) {
            val fragment = supportFragmentManager.findFragmentByTag("f$i") as? TestQuestionFragment
            if (fragment?.isAnswerSelected() == false) return false
        }
        return true
    }

    private fun showUnansweredQuestionsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Неотвеченные вопросы")
            .setMessage("Вы ответили не на все вопросы. Завершить тест?")
            .setPositiveButton("Да") { dialog: DialogInterface, _: Int ->
                calculateResults()
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .show()
    }




    private fun calculateResults() {
        val totalQuestions = adapter.itemCount
        var correctAnswers = 0

        for (i in 0 until adapter.itemCount) {
            val fragment = supportFragmentManager
                .findFragmentByTag("f$i") as? TestQuestionFragment
            if (fragment?.isAnswerCorrect() == true) {
                correctAnswers++
            }
        }

        // Только передача данных, без сохранения
        val resultIntent = Intent(this, TestResultActivity::class.java).apply {
            putExtra("totalQuestions", totalQuestions)
            putExtra("correctAnswers", correctAnswers)
            putExtra("testTitle", adapter.getTestTitle())
        }
        startActivity(resultIntent)
        finish()
    }






}


