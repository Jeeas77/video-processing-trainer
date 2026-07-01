package com.example.vkr2025

import com.example.vkr2025.TestResultRepository
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vkr2025.databinding.ItemTestResultBinding

class ResultsAdapter(private var results: List<TestResultRepository.TestResult>) :
    RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(val binding: ItemTestResultBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemTestResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.binding.apply {
            testName.text = result.testName
            testDate.text = result.date
            testScore.text = "${result.correctAnswers}/${result.totalQuestions} (${result.percentage}%)"
            progressBar.max = 100
            progressBar.progress = result.percentage
        }
    }

    fun updateResults(newResults: List<TestResultRepository.TestResult>) {
        results = newResults
        notifyDataSetChanged()
    }

    override fun getItemCount() = results.size
}
