package com.example.vkr2025

import androidx.core.content.ContextCompat
import androidx.annotation.DrawableRes
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vkr2025.databinding.FragmentTextSectionBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class TextSectionFragment : Fragment() {
    private var _binding: FragmentTextSectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            // Установка заголовка
            binding.titleText.text = args.getString("title") ?: ""

            // Очистка контейнера
            binding.contentContainer.removeAllViews()

            // Добавление основного изображения (если есть)
            args.getInt("header_image").takeIf { it != 0 }?.let { resId ->
                addImageView(resId, isHeader = true)
            }

            // Добавление контента
            (args.getSerializable("content_items") as? ArrayList<LearningPagerAdapter.ContentItem>)?.forEach { item ->
                when {
                    !item.text.isNullOrEmpty() -> addTextView(item.text)
                    item.imageRes != null -> addImageView(item.imageRes, isHeader = false)
                }
            }
        }
    }

    private fun addTextView(text: String) {
        TextView(requireContext()).apply {
            this.text = text
            setTextAppearance(requireContext(), android.R.style.TextAppearance_Material_Body1)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16.dpToPx(), 0, 16.dpToPx())
            }
            binding.contentContainer.addView(this)
        }
    }

    private fun addImageView(@DrawableRes resId: Int, isHeader: Boolean) {
        ImageView(requireContext()).apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), resId))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                if (isHeader) resources.getDimensionPixelSize(R.dimen.header_image_height)
                else resources.getDimensionPixelSize(R.dimen.content_image_height)
            ).apply {
                setMargins(0, 8.dpToPx(), 0, 16.dpToPx())
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
            binding.contentContainer.addView(this)
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
