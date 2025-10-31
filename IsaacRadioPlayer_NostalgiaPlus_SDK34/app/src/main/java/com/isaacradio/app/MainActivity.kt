package com.isaacradio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.isaacradio.app.databinding.ActivityMainBinding
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var player: ExoPlayer? = null

    private val streamUrl = "https://listen.samcloud.com/w/143377?type=mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener { startPlayback() }
        binding.stopButton.setOnClickListener { stopPlayback() }
    }

    private fun startPlayback() {
        if (player == null) {
            player = ExoPlayer.Builder(this).build().also { p ->
                val item = MediaItem.fromUri(streamUrl)
                p.setMediaItem(item)
                p.prepare()
            }
        }
        player?.playWhenReady = true
        binding.statusText.text = getString(R.string.status_playing)
    }

    private fun stopPlayback() {
        player?.stop()
        binding.statusText.text = getString(R.string.status_stopped)
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}