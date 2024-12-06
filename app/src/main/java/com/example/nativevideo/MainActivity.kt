package com.example.nativevideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.video_preview)
        playButton = findViewById(R.id.play_button)
        // Initialize ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer
        playButton.setOnClickListener {
            initVideo() // Trigger video playback
        }
    }

    @OptIn(UnstableApi::class)
    private fun initVideo() {
        // Create MediaSource with headers
        val mediaSource = buildMediaSourceWithHeaders(
            url = "https://stage.wdw-photopass.wdprapps.disney.com/media-url-shortener-service/url/v2/GIsypQEe_0urJ_CCeJzKeA", // Replace with your video URL
            token = "BEARER eyJraWQiOiJhNDU3MjNjZi02MjlkLTQ2ZTUtOTQyYy05ZTQ4NzhmNzZmYzgiLCJhbGciOiJFUzI1NiJ9.eyJqdGkiOiJjR2lQU1d6dFFSQnFNcFdzWUpTNlhnIiwiaXNzIjoiaHR0cHM6Ly9zdGcuYXV0aC5yZWdpc3RlcmRpc25leS5nby5jb20iLCJhdWQiOiJ1cm46ZGlzbmV5Om9uZWlkOnN0ZyIsInN1YiI6Ins3ODExN0VEOS05MDBCLTRCODItODk5Mi1DMTQ2Mjg5QjA3MTd9IiwiaWF0IjoxNzMzNDk2NTc1LCJuYmYiOjE3MzMyNjQxMTgsImV4cCI6MTczMzU4Mjk3NSwiY2xpZW50X2lkIjoiVFBSLUdSSURERVYuQU5ELVNUQUdFIiwibGlkIjoiNDllZmMyZjAtYjNiZS00NjlkLTljNjYtNTg5MWFhMzY2YTU0IiwiY2F0IjoiZ3Vlc3QifQ.YjKsQrdg7vRXzn-_uWBwlzmQpqnzIJoK0DuayFSXYuh2AjzVSb3_JqaKl05Nqtc52z6GcgsVKehtqNC5jgJsmA"       // Replace with your actual token
        )

        // Prepare and play
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    @OptIn(UnstableApi::class) private fun buildMediaSourceWithHeaders(url: String, token: String): MediaSource {
        // Create the HTTP DataSource Factory with headers
        val dataSourceFactory = DefaultHttpDataSource.Factory().apply {
            setDefaultRequestProperties(
                mapOf("Authorization" to token)
            )
        }

        // Create the MediaSource Factory
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)

        // Build the MediaSource
        return mediaSourceFactory.createMediaSource(MediaItem.fromUri(url))
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release() // Release the player
    }
}