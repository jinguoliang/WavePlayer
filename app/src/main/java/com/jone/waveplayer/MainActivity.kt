package com.jone.waveplayer

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleRate = 44100
        val channel = AudioFormat.CHANNEL_OUT_STEREO
        val encodingBit = AudioFormat.ENCODING_PCM_FLOAT
        val sampleRateForCenterC = 261

        val waveGenerator = WaveGenerator(1f, sampleRateForCenterC)

        val minBuffSize = AudioTrack.getMinBufferSize(
            sampleRate,
            channel,
            encodingBit
        )
        val player = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(encodingBit)
                    .setSampleRate(sampleRate)
                    .setChannelMask(channel)
                    .build()
            )
            .setBufferSizeInBytes(minBuffSize)
            .build()
        player.play()
        Thread {
            val audioData = FloatArray(minBuffSize)
            var startTime = 0f
            while (!isDestroyed) {
                val duration = waveGenerator.generateWave(sampleRate, startTime, audioData)
                player.write(audioData, 0, minBuffSize, AudioTrack.WRITE_BLOCKING)
                startTime += duration
            }
            player.stop()
        }.start()
    }
}
