package com.example.vkr2025

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.example.vkr2025.databinding.FragmentVideoSectionBinding

class VideoSectionFragment : Fragment() {

    private var _binding: FragmentVideoSectionBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    companion object {
        fun newInstance(title: String, videoUrl: String, thumbnailUrl: String? = null): VideoSectionFragment {
            return VideoSectionFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("videoUrl", videoUrl)
                    putString("thumbnailUrl", thumbnailUrl)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            binding.sectionTitle.text = args.getString("title", "")
            val videoUrl = args.getString("videoUrl", "")
            val thumbnailUrl = args.getString("thumbnailUrl")

            // Загрузка миниатюры
            thumbnailUrl?.takeIf { it.isNotEmpty() }?.let { url ->
                Glide.with(this)
                    .load(url)
                    .into(binding.thumbnailImage)
                binding.thumbnailImage.visibility = View.VISIBLE
            }

            initializePlayer(videoUrl)
        }
    }

    private fun initializePlayer(videoUrl: String) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.playerView.player = exoPlayer
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = playWhenReady
            exoPlayer.seekTo(currentWindow, playbackPosition)
            exoPlayer.prepare()

            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        binding.thumbnailImage.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentWindow = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun onStart() {
        super.onStart()
        if (player == null) {
            arguments?.getString("videoUrl")?.let { initializePlayer(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
        _binding = null
    }
}