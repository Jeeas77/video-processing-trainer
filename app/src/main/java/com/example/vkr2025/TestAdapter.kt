package com.example.vkr2025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vkr2025.databinding.ItemTestBinding

class TestAdapter(
    private val tests: List<TestSelectionActivity.Test>,
    private val onItemClick: (TestSelectionActivity.Test) -> Unit
) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    inner class TestViewHolder(val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val binding = ItemTestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val test = tests[position]
        holder.binding.apply {
            testTitle.text = test.title
            testDescription.text = test.description
            testIcon.setImageResource(test.iconRes)

            root.setOnClickListener { onItemClick(test) }
        }
    }

    override fun getItemCount() = tests.size
}
