package com.jone.waveplayer

import com.google.common.truth.Truth.assertThat
import org.junit.Test

import org.junit.Before
import java.nio.ByteBuffer

class WaveGeneratorTest {

    private val RATE_ONE = 1 // 一秒一次说明，一秒一个正弦
    private val RATE_TWO = 2 // 一秒一次说明，一秒一个正弦

    private lateinit var generator: WaveGenerator


    @Before
    fun setup() {
    }

    @Test
    fun `计算某个时间点的振幅`() {
        generator = WaveGenerator(1f, RATE_ONE)

        assertThat(generator.getAmplitude(0f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.25f)).isWithin(0.001f).of(1f)
        assertThat(generator.getAmplitude(0.5f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.75f)).isWithin(0.001f).of(-1f)
        assertThat(generator.getAmplitude(1f)).isWithin(0.001f).of(0f)

        generator = WaveGenerator(1f, RATE_TWO)

        assertThat(generator.getAmplitude(0f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.25f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.5f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.75f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(1f)).isWithin(0.001f).of(0f)
        assertThat(generator.getAmplitude(0.125f)).isWithin(0.001f).of(1f)
        assertThat(generator.getAmplitude(0.375f)).isWithin(0.001f).of(-1f)

    }

    @Test
    fun `生成正弦波数据存入 buffer`() {
        generator = WaveGenerator(1f, RATE_ONE)
        val buffer = FloatArray(2)
        generator.generateWave(1, 0f, buffer)
        assertThat(buffer).usingTolerance(0.0001).containsExactly(0f, 0f)

        generator.generateWave(2, 0f, buffer)
        assertThat(buffer).usingTolerance(0.0001).containsExactly(0f, 0f)

        generator.generateWave(4, 0f, buffer)
        assertThat(buffer).usingTolerance(0.0001).containsExactly(0f, 1f)

        generator.generateWave(1, 0.25f, buffer)
        assertThat(buffer).usingTolerance(0.0001).containsExactly(1f, 1f)

        generator.generateWave(2, 0.75f, buffer)
        assertThat(buffer).usingTolerance(0.0001).containsExactly(-1f, 1f)
    }
}