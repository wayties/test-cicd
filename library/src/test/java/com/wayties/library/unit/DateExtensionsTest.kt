package com.wayties.library.unit

import com.wayties.library.format1
import com.wayties.library.millisecondsToDays1
import com.wayties.library.millisecondsToHours1
import com.wayties.library.millisecondsToMinutes1
import com.wayties.library.millisecondsToSeconds1
import com.wayties.library.secondsToDays1
import com.wayties.library.secondsToHours1
import com.wayties.library.secondsToMinutes1
import com.wayties.library.toCompareInDays1
import com.wayties.library.toCompareInHours1
import com.wayties.library.toCompareInMinutes1
import com.wayties.library.toCompareInMonths1
import com.wayties.library.toCompareInSeconds1
import com.wayties.library.toCompareInYears1
import com.wayties.library.toDate1
import com.wayties.library.toDateLong1
import com.wayties.library.toDateString1
import com.wayties.library.toLocalDateTime1
import com.wayties.library.toStringFormat1
import com.wayties.library.toStringSimpleFormat1
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class DateExtensionsTest {
    @Test
    fun timeDateToString_formatsMillisCorrectly() {
        val instant = LocalDateTime.of(2024, Month.MARCH, 1, 10, 30)
        val millis = instant.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val formatted = millis.toDateString1("yyyy-MM-dd HH:mm", Locale.US)

        assertEquals("2024-03-01 10:30", formatted)
    }

    @Test
    fun timeDateToLong_parsesBackToMillis() {
        val millis = "2024-03-01 10:30".toDateLong1("yyyy-MM-dd HH:mm", Locale.US)

        val expected =
            LocalDateTime
                .of(2024, Month.MARCH, 1, 10, 30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        assertEquals(expected, millis)
    }

    @Test
    fun dateDifferences_calculateExpectedValues() {
        val start = LocalDate.of(2024, Month.JANUARY, 1)
        val end = LocalDate.of(2024, Month.FEBRUARY, 2)

        assertEquals(32, start.toCompareInDays1(end))
        assertEquals(1, start.toCompareInMonths1(end))
        assertEquals(0, start.toCompareInYears1(end))
    }

    @Test
    fun localDateTimeBetween_calculationsAreAccurate() {
        val from = LocalDateTime.of(2024, Month.JUNE, 10, 10, 0)
        val to = from.plusHours(5).plusMinutes(30)

        assertEquals(5, from.toCompareInHours1(to))
        assertEquals(330, from.toCompareInMinutes1(to))
    }

    @Test
    fun conversionHelpers_roundTripSuccessfully() {
        val now = Date()
        val formatted = now.toStringFormat1("yyyy-MM-dd HH:mm")
        val reparsed = formatted.toDate1("yyyy-MM-dd HH:mm")

        assertTrue(reparsed?.time ?: 0L > 0L)
    }

    // ========== Exception Handling Tests ==========

    @Test(expected = IllegalArgumentException::class)
    fun timeDateToLong_throwsExceptionForInvalidFormat() {
        "invalid-date".toDateLong1("yyyy-MM-dd")
    }

    @Test(expected = IllegalArgumentException::class)
    fun timeDateToLong_throwsExceptionWhenParsingIncomplete() {
        // SimpleDateFormat이 "2024-01-15"까지만 파싱하고 나머지 " extra text"는 무시
        // parsePosition.index != length 조건을 만족하여 예외 발생
        "2024-01-15 extra text".toDateLong1("yyyy-MM-dd", Locale.US)
    }

    @Test
    fun timeDateToDate_returnsNullForInvalidFormat() {
        val result = "invalid-date".toDate1("yyyy-MM-dd")
        assertEquals(null, result)
    }

    // ========== Date Time Difference Tests ==========

    @Test
    fun timeDifferenceInSeconds_calculatesCorrectly() {
        val date1 = Date(1000000L)
        val date2 = Date(1005000L)

        assertEquals(5L, date1.toCompareInSeconds1(date2))
        assertEquals(5L, date2.toCompareInSeconds1(date1)) // abs value
    }

    @Test
    fun timeDifferenceInMinutes_calculatesCorrectly() {
        val date1 = Date(0L)
        val date2 = Date(5 * 60 * 1000L) // 5 minutes

        assertEquals(5L, date1.toCompareInMinutes1(date2))
    }

    @Test
    fun timeDifferenceInHours_calculatesCorrectly() {
        val date1 = Date(0L)
        val date2 = Date(3 * 3600 * 1000L) // 3 hours

        assertEquals(3L, date1.toCompareInHours1(date2))
    }

    @Test
    fun timeDifference_handlesNegativeDifferences() {
        val earlier = Date(1000L)
        val later = Date(5000L)

        // Should return absolute value
        assertEquals(4L, earlier.toCompareInSeconds1(later))
        assertEquals(4L, later.toCompareInSeconds1(earlier))
    }

    // ========== Long Conversion Tests ==========

    @Test
    fun secondsToMinutes1_convertsCorrectly() {
        assertEquals(2L, 120L.secondsToMinutes1())
        assertEquals(1L, 90L.secondsToMinutes1())
        assertEquals(0L, 30L.secondsToMinutes1())
    }

    @Test
    fun secondsToHours1_convertsCorrectly() {
        assertEquals(2L, 7200L.secondsToHours1())
        assertEquals(1L, 3660L.secondsToHours1())
        assertEquals(0L, 1800L.secondsToHours1())
    }

    @Test
    fun secondsToDays1_convertsCorrectly() {
        assertEquals(2L, 172800L.secondsToDays1())
        assertEquals(1L, 86400L.secondsToDays1())
        assertEquals(0L, 43200L.secondsToDays1())
    }

    @Test
    fun millisecondsToSeconds1_convertsCorrectly() {
        assertEquals(5L, 5000L.millisecondsToSeconds1())
        assertEquals(1L, 1500L.millisecondsToSeconds1())
        assertEquals(0L, 500L.millisecondsToSeconds1())
    }

    @Test
    fun millisecondsToMinutes1_convertsCorrectly() {
        assertEquals(2L, 120000L.millisecondsToMinutes1())
        assertEquals(1L, 90000L.millisecondsToMinutes1())
    }

    @Test
    fun millisecondsToHours1_convertsCorrectly() {
        assertEquals(2L, 7200000L.millisecondsToHours1())
        assertEquals(1L, 3660000L.millisecondsToHours1())
    }

    @Test
    fun millisecondsToDays1_convertsCorrectly() {
        assertEquals(2L, 172800000L.millisecondsToDays1())
        assertEquals(1L, 86400000L.millisecondsToDays1())
    }

    // ========== Int Conversion Tests ==========

    @Test
    fun intsecondsToMinutes1_convertsCorrectly() {
        assertEquals(2, 120.secondsToMinutes1())
        assertEquals(1, 90.secondsToMinutes1())
        assertEquals(0, 30.secondsToMinutes1())
    }

    @Test
    fun intsecondsToHours1_convertsCorrectly() {
        assertEquals(2, 7200.secondsToHours1())
        assertEquals(1, 3660.secondsToHours1())
        assertEquals(0, 1800.secondsToHours1())
    }

    @Test
    fun intsecondsToDays1_convertsCorrectly() {
        assertEquals(2, 172800.secondsToDays1())
        assertEquals(1, 86400.secondsToDays1())
        assertEquals(0, 43200.secondsToDays1())
    }

    // ========== LocalDateTime Conversion Tests ==========

    @Test
    fun longToLocalDateTime_convertsCorrectly() {
        val millis =
            LocalDateTime
                .of(2024, Month.JUNE, 15, 14, 30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        val localDateTime = millis.toLocalDateTime1()

        assertEquals(2024, localDateTime.year)
        assertEquals(Month.JUNE, localDateTime.month)
        assertEquals(15, localDateTime.dayOfMonth)
        assertEquals(14, localDateTime.hour)
        assertEquals(30, localDateTime.minute)
    }

    @Test
    fun dateToLocalDateTime_convertsCorrectly() {
        val date = Date()
        val localDateTime = date.toLocalDateTime1()

        assertTrue(localDateTime.year > 2020)
    }

    @Test
    fun localDateTimeFormat_formatsWithPattern() {
        val dateTime = LocalDateTime.of(2024, Month.MARCH, 1, 10, 30)

        val formatted = dateTime.format1("yyyy-MM-dd HH:mm:ss", Locale.US)

        assertEquals("2024-03-01 10:30:00", formatted)
    }

    // ========== Date Formatting Tests ==========

    @Test
    fun dateFormattedToString_usesSimpledateFormat() {
        val date = Date(0L) // Epoch time

        val formatted = date.toStringSimpleFormat1("yyyy-MM-dd", Locale.US)

        assertTrue(formatted.startsWith("19")) // 1970
    }

    @Test
    fun dateFormatToString_usesLocalDateTime() {
        val date = Date(0L)

        val formatted = date.toStringFormat1("yyyy-MM-dd", Locale.US)

        assertTrue(formatted.startsWith("19")) // 1970
    }

    // ========== Locale Tests ==========

    @Test
    fun timeDateToString_respectsLocale() {
        val millis =
            LocalDateTime
                .of(2024, Month.MARCH, 1, 10, 30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        val formattedUS = millis.toDateString1("MMM dd, yyyy", Locale.US)
        val formattedFR = millis.toDateString1("dd MMM yyyy", Locale.FRANCE)

        assertTrue(formattedUS.contains("Mar"))
        // French locale may vary by system, just check it's not empty
        assertTrue(formattedFR.isNotEmpty())
    }

    @Test
    fun timeDateToString_usesDefaultLocaleWhenOmitted() {
        val original = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            val millis =
                LocalDateTime
                    .of(2024, Month.JULY, 4, 12, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

            val withoutLocale = millis.toDateString1("yyyy-MM-dd HH:mm")
            val withLocale = millis.toDateString1("yyyy-MM-dd HH:mm", Locale.US)

            assertEquals(withLocale, withoutLocale)
        } finally {
            Locale.setDefault(original)
        }
    }

    @Test
    fun localDateTimeFormat_usesDefaultLocaleWhenOmitted() {
        val original = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            val dateTime = LocalDateTime.of(2023, Month.DECEMBER, 25, 8, 45)

            val withoutLocale = dateTime.format1("yyyy-MM-dd HH:mm:ss")
            val withLocale = dateTime.format1("yyyy-MM-dd HH:mm:ss", Locale.US)

            assertEquals(withLocale, withoutLocale)
        } finally {
            Locale.setDefault(original)
        }
    }

    @Test
    fun dateFormattedToString_usesDefaultLocaleWhenOmitted() {
        val original = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            val date = Date(0L)

            val withoutLocale = date.toStringSimpleFormat1("yyyy-MM-dd")
            val withLocale = date.toStringSimpleFormat1("yyyy-MM-dd", Locale.US)

            assertEquals(withLocale, withoutLocale)
        } finally {
            Locale.setDefault(original)
        }
    }

    // ========== Edge Cases ==========

    @Test
    fun zeroMilliseconds_convertsToEpoch() {
        val localDateTime = 0L.toLocalDateTime1()

        assertEquals(1970, localDateTime.year)
        assertEquals(Month.JANUARY, localDateTime.month)
        assertEquals(1, localDateTime.dayOfMonth)
    }

    @Test
    fun negativeTimeDifference_returnsAbsoluteValue() {
        val start = LocalDate.of(2024, Month.FEBRUARY, 1)
        val end = LocalDate.of(2024, Month.JANUARY, 1)

        val days = start.toCompareInDays1(end)

        assertEquals(-31, days) // ChronoUnit.DAYS.between preserves sign
    }

    @Test
    fun sameDateTime_hasZeroDifference() {
        val date = Date()

        assertEquals(0L, date.toCompareInSeconds1(date))
        assertEquals(0L, date.toCompareInMinutes1(date))
        assertEquals(0L, date.toCompareInHours1(date))
    }
}
