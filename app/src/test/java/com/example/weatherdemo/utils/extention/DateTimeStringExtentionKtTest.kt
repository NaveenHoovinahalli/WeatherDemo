package com.example.weatherdemo.utils.extention

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt


class DateTimeStringExtentionKtTest {


        @Test
        fun `toUtcDateTimeString should format epoch seconds correctly`() {
            // Given
            val epochSecond = 1672531199 // Corresponds to a specific date

            // When
            val result = epochSecond.toUtcDateTimeString()

            // Then
            val expected = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(epochSecond.toLong()),
                ZoneId.of("Asia/Kolkata")
            ).format(DateTimeFormatter.ofPattern("MMM dd"))
            assertEquals(expected, result)
        }

        @Test
        fun `toUtcTimeString should format epoch seconds correctly`() {
            // Given
            val epochSecond = 1672531199 // Corresponds to a specific time

            // When
            val result = epochSecond.toUtcTimeString()

            // Then
            val expected = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(epochSecond.toLong()),
                ZoneId.of("Asia/Kolkata")
            ).format(DateTimeFormatter.ofPattern("HH:mm"))
            assertEquals(expected, result)
        }

        @Test
        fun `toCelsius should convert Kelvin to Celsius correctly`() {
            // Given
            val kelvin = 300.0

            // When
            val result = kelvin.toCelsius()

            // Then
            val expected = (kelvin - 273.15).roundToInt()
            assertEquals(expected, result)
        }

        @Test
        fun `meterPerSecondToKilometerPerHour should convert ms to kmh correctly`() {
            // Given
            val meterPerSecond = 10.0

            // When
            val result = meterPerSecondToKilometerPerHour(meterPerSecond)

            // Then
            val expected = (meterPerSecond * 3.6).toInt()
            assertEquals(expected, result)
        }

        @Test
        fun `isDayTime should return correct value based on current hour`() {
            // Given
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            // Test if it's daytime between 6 AM and 8 PM
            val expected = currentHour in 6 until 20

            // When
            val result = isDayTime()

            // Then
            assertEquals(expected, result)
        }


        private fun meterPerSecondToKilometerPerHour(meterPerSecond: Double): Int {
            return (meterPerSecond * 3.6).toInt()
        }

        @Test
        fun testZeroMetersPerSecond() {
            val result = meterPerSecondToKilometerPerHour(0.0)
            assertEquals(0, result)
        }

        @Test
        fun testOneMeterPerSecond() {
            val result = meterPerSecondToKilometerPerHour(1.0)
            assertEquals(3, result)
        }

        @Test
        fun testFiveMetersPerSecond() {
            val result = meterPerSecondToKilometerPerHour(5.0)
            assertEquals(18, result)
        }

        @Test
        fun testNegativeSpeed() {
            val result = meterPerSecondToKilometerPerHour(-2.5)
            assertEquals(-9, result)
        }

        @Test
        fun testNonIntegerSpeed() {
            val result = meterPerSecondToKilometerPerHour(2.75)
            assertEquals(9, result)
        }

        @Test
        fun testLargeSpeed() {
            val result = meterPerSecondToKilometerPerHour(1000.0)
            assertEquals(3600, result)
        }


}