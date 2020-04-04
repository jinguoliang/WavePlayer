package com.jone.waveplayer

import kotlin.math.PI
import kotlin.math.sin

class WaveGenerator(val maxAmplitude: Float, val rate: Int) {

    fun getAmplitude(time: Float): Float {
        val cycle = PI.toFloat() * 2
        val radiansPerSecond = rate * cycle
        return sin(time * radiansPerSecond) * maxAmplitude
    }

    fun generateWave(sampleRate: Int, startTime: Float, buffer: FloatArray): Float {
        val sampleInterDuration = 1f / sampleRate
        buffer.forEachIndexed { index, _ ->
            buffer[index] = getAmplitude(startTime + sampleInterDuration * index)
        }
        return sampleInterDuration * buffer.size
    }
}