package com.example.vkr2025

import com.example.vkr2025.TestResultRepository
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkr2025.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private lateinit var adapter: ResultsAdapter
    private val repository by lazy { TestResultRepository.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        adapter = ResultsAdapter(repository.getAllResults())
        binding.resultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ResultsActivity)
            adapter = this@ResultsActivity.adapter
        }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnClearResults.setOnClickListener {
            repository.clearResults() // Изменили clearAllResults на clearResults
            adapter.updateResults(emptyList())
        }
    }
}


