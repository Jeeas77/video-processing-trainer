package com.example.vkr2025

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.io.Serializable

class LearningPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val sections: List<LearningSection>
) : FragmentStateAdapter(fragmentActivity) {

    sealed class LearningSection {
        data class TextSection(
            val title: String,
            val content: List<ContentItem>,
            @DrawableRes val imageRes: Int? = null
        ) : LearningSection()

        data class VideoSection(
            val title: String,
            val videoUrl: String,
            val thumbnailUrl: String? = null
        ) : LearningSection()
    }

    data class ContentItem(
        val text: String? = null,
        @DrawableRes val imageRes: Int? = null
    ) : Serializable

    override fun getItemCount(): Int = sections.size

    override fun createFragment(position: Int): Fragment {
        return when (val section = sections[position]) {
            is LearningSection.TextSection -> TextSectionFragment().apply {
                arguments = Bundle().apply {
                    putString("title", section.title)
                    putSerializable("content_items", ArrayList(section.content))
                    section.imageRes?.let { putInt("header_image", it) }
                }
            }
            is LearningSection.VideoSection -> VideoSectionFragment().apply {
                arguments = Bundle().apply {
                    putString("title", section.title)
                    putString("video_url", section.videoUrl)
                    section.thumbnailUrl?.let { putString("thumbnail_url", it) }
                }
            }
        }
    }
}
