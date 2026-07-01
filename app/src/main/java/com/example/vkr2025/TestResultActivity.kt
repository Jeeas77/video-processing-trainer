package com.example.vkr2025

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr2025.databinding.ActivityTestResultBinding

class TestResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding
    private val repository by lazy { TestResultRepository.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val total = intent.getIntExtra("totalQuestions", 0)
        val correct = intent.getIntExtra("correctAnswers", 0)
        val testName = intent.getStringExtra("testTitle") ?: "Основной тест"

        // Сохранение только если это новый результат
        if (shouldSaveResult(testName, correct, total)) {
            repository.saveTestResult(
                TestResultRepository.TestResult.create(testName, correct, total)
            )
        }

        displayResults(correct, total)
        setupRestartButton()
    }

    private fun shouldSaveResult(testName: String, correct: Int, total: Int): Boolean {
        if (total <= 0) return false

        val lastResult = repository.getAllResults().firstOrNull()
        return lastResult?.let {
            it.testName != testName ||
                    it.correctAnswers != correct ||
                    it.totalQuestions != total
        } ?: true
    }

    private fun displayResults(correct: Int, total: Int) {
        val percentage = if (total > 0) (correct.toFloat() / total * 100).toInt() else 0
        binding.resultTextView.text = "Результат: $correct/$total ($percentage%)"
        binding.progressBar.apply {
            max = 100
            progress = percentage
        }
    }

    private fun setupRestartButton() {
        binding.restartButton.setOnClickListener { finish() }
    }
}

