package com.example.vkr2025

import androidx.core.content.ContextCompat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vkr2025.databinding.FragmentTestQuestionBinding

class TestQuestionFragment : Fragment() {

    private var _binding: FragmentTestQuestionBinding? = null
    private val binding get() = _binding!!
    private var selectedAnswerIndex = -1
    private var correctAnswerIndex = -1

    companion object {
        fun newInstance(question: String, options: List<String>, correctIndex: Int): TestQuestionFragment {
            return TestQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString("question", question)
                    putStringArrayList("options", ArrayList(options))
                    putInt("correctIndex", correctIndex)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.questionTextView.text = it.getString("question", "")
            val options = it.getStringArrayList("options") ?: emptyList()
            correctAnswerIndex = it.getInt("correctIndex", -1)

            // Устанавливаем текст вариантов ответов
            binding.option1TextView.text = options.getOrElse(0) { "" }
            binding.option2TextView.text = options.getOrElse(1) { "" }
            binding.option3TextView.text = options.getOrElse(2) { "" }

            // Настройка обработчиков кликов
            setupOptionClicks()
        }
    }

    private fun setupOptionClicks() {
        binding.option1Card.setOnClickListener { selectOption(0) }
        binding.option2Card.setOnClickListener { selectOption(1) }
        binding.option3Card.setOnClickListener { selectOption(2) }
    }

    private fun selectOption(index: Int) {
        selectedAnswerIndex = index
        updateOptionAppearance()
    }

    private fun updateOptionAppearance() {
        val cards = listOf(binding.option1Card, binding.option2Card, binding.option3Card)

        cards.forEachIndexed { index, card ->
            card.strokeWidth = if (index == selectedAnswerIndex) 4 else 1
            card.strokeColor = ContextCompat.getColor(requireContext(),
                if (index == selectedAnswerIndex) R.color.selected_color else R.color.unselected_color)
        }
    }

    fun isAnswerCorrect(): Boolean {
        return selectedAnswerIndex == correctAnswerIndex
    }

    fun isAnswerSelected(): Boolean {
        return selectedAnswerIndex != -1
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}