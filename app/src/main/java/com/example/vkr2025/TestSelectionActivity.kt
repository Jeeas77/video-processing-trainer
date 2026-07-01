package com.example.vkr2025


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkr2025.databinding.ActivityTestSelectionBinding


class TestSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestSelectionBinding
    private lateinit var adapter: TestAdapter

    // Выносим список тестов в companion object для удобства доступа из других классов
    companion object {
        val availableTests = listOf(
            Test(
                id = 1,
                title = "Технологии и обработка аудио и видеоинформации",
                description = "10 вопросов",
                iconRes = R.drawable.ic_test,
                questionCount = 10
            ),
            Test(
                id = 2,
                title = "Технология мультимедиа",
                description = "10 вопросов",
                iconRes = R.drawable.ic_test,
                questionCount = 10
            ),
            Test(
                id = 3,
                title = "Технологии обработки видеоинформации",
                description = "8 вопросов",
                iconRes = R.drawable.ic_test,
                questionCount = 8
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Выберите тест"
    }

    private fun setupRecyclerView() {
        adapter = TestAdapter(availableTests) { selectedTest ->
            navigateToTestingActivity(selectedTest)
        }

        with(binding.testsRecyclerView) {
            layoutManager = LinearLayoutManager(this@TestSelectionActivity)
            adapter = this@TestSelectionActivity.adapter
            setHasFixedSize(true) // Улучшает производительность
        }
    }

    private fun navigateToTestingActivity(selectedTest: Test) {
        Intent(this, TestingActivity::class.java).apply {
            putExtra("testTitle", selectedTest.title)
            putExtra("testId", selectedTest.id) // Добавляем ID для возможной будущей логики
            putExtra("questionCount", selectedTest.questionCount)
            startActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    data class Test(
        val id: Int, // Уникальный идентификатор теста
        val title: String,
        val description: String,
        val iconRes: Int,
        val questionCount: Int // Количество вопросов в тесте
    )
}
