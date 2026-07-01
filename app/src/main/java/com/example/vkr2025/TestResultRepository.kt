package com.example.vkr2025


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class TestResultRepository private constructor(private val context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: TestResultRepository? = null

        fun getInstance(context: Context): TestResultRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TestResultRepository(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }


    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("TestResults", Context.MODE_PRIVATE)
    }

    private val gson: Gson by lazy { Gson() }
    private val dateFormat by lazy {
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    }

    fun saveTestResult(result: TestResult) {
        if (result.totalQuestions <= 0) return

        val allResults = getAllResults().toMutableList()

        // Проверяем, не является ли результат дубликатом последнего
        if (allResults.firstOrNull()?.let {
                it.testName == result.testName &&
                        it.correctAnswers == result.correctAnswers &&
                        it.totalQuestions == result.totalQuestions
            } != true) {
            allResults.add(0, result)
            // Ограничиваем историю 50 записями
            if (allResults.size > 50) allResults.removeLast()

            prefs.edit().putString("test_results", gson.toJson(allResults)).apply()
        }
    }


    fun getAllResults(): List<TestResult> {
        return try {
            prefs.getString("test_results", null)?.let { json ->
                gson.fromJson(json, object : TypeToken<List<TestResult>>() {}.type)
            } ?: emptyList()
        } catch (e: Exception) {
            // В случае ошибки парсинга очищаем хранилище
            clearResults()
            emptyList()
        }
    }

    fun clearResults() {
        prefs.edit().remove("test_results").apply()
    }

    fun getBestResult(): TestResult? {
        return getAllResults().maxByOrNull { it.percentage }
    }

    data class TestResult(
        val id: String = UUID.randomUUID().toString(),
        val testName: String,
        val date: String,
        val correctAnswers: Int,
        val totalQuestions: Int,
        val percentage: Int
    ) {
        companion object {
            fun create(testName: String, correct: Int, total: Int): TestResult {
                require(total > 0) { "Total questions must be positive" }
                require(correct in 0..total) { "Correct answers must be between 0 and total" }

                val date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
                val percentage = (correct.toFloat() / total * 100).toInt()

                return TestResult(
                    id = "${testName}_${System.currentTimeMillis()}",
                    testName = testName,
                    date = date,
                    correctAnswers = correct,
                    totalQuestions = total,
                    percentage = percentage
                )
            }
        }
    }
}