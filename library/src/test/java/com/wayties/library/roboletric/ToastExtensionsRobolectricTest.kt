package com.wayties.library.roboletric

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ApplicationProvider
import com.wayties.library.toastLong1
import com.wayties.library.toastShort1
import com.wayties.library.toastShowLong1
import com.wayties.library.toastShowShort1
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

/**
 * Robolectric tests for Toast extension functions
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ToastExtensionsRobolectricTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    // ========================================
    // Context.toastShort1() Tests
    // ========================================

    @Test
    fun toastShort1_createsShortToast() {
        val message = "Short Toast"
        val toast = context.toastShort1(message)

        assertEquals(Toast.LENGTH_SHORT, toast.duration)
    }

    @Test
    fun toastShort1_withEmptyMessage_createsToast() {
        val toast = context.toastShort1("")

        assertEquals(Toast.LENGTH_SHORT, toast.duration)
    }

    // ========================================
    // Context.toastLong1() Tests
    // ========================================

    @Test
    fun toastLong1_createsLongToast() {
        val message = "Long Toast"
        val toast = context.toastLong1(message)

        assertEquals(Toast.LENGTH_LONG, toast.duration)
    }

    @Test
    fun toastLong1_withLongMessage_createsToast() {
        val longMessage = "a".repeat(1000)
        val toast = context.toastLong1(longMessage)

        assertEquals(Toast.LENGTH_LONG, toast.duration)
    }

    // ========================================
    // Context.toastShowShort1() Tests
    // ========================================

    @Test
    fun toastShowShort1_showsShortToast() {
        val message = "Show Short"
        context.toastShowShort1(message)

        val latestToast = ShadowToast.getLatestToast()
        assertEquals(Toast.LENGTH_SHORT, latestToast.duration)
        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun toastShowShort1_withSpecialCharacters_showsToast() {
        val message = "Hello\nWorld\t!"
        context.toastShowShort1(message)

        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    // ========================================
    // Context.toastShowLong1() Tests
    // ========================================

    @Test
    fun toastShowLong1_showsLongToast() {
        val message = "Show Long"
        context.toastShowLong1(message)

        val latestToast = ShadowToast.getLatestToast()
        assertEquals(Toast.LENGTH_LONG, latestToast.duration)
        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun toastShowLong1_withNumbers_showsToast() {
        val message = "Count: 12345"
        context.toastShowLong1(message)

        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    // ========================================
    // Fragment Toast Tests
    // ========================================

    @Test
    fun fragment_toastShowShort1_withContext_showsToast() {
        val activity =
            Robolectric
                .buildActivity(FragmentActivity::class.java)
                .create()
                .start()
                .resume()
                .get()
        val fragment = Fragment()
        activity.supportFragmentManager
            .beginTransaction()
            .add(fragment, "test")
            .commitNow()

        val message = "Fragment Short"
        fragment.toastShowShort1(message)

        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun fragment_toastShowLong1_withContext_showsToast() {
        val activity =
            Robolectric
                .buildActivity(FragmentActivity::class.java)
                .create()
                .start()
                .resume()
                .get()
        val fragment = Fragment()
        activity.supportFragmentManager
            .beginTransaction()
            .add(fragment, "test")
            .commitNow()

        val message = "Fragment Long"
        fragment.toastShowLong1(message)

        assertEquals(message, ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun fragment_toastShowShort1_withoutContext_doesNotCrash() {
        val fragment = Fragment() // No context

        // Should not crash, just log error
        fragment.toastShowShort1("Test")

        // No assertion needed - just verify no crash
    }

    @Test
    fun fragment_toastShowLong1_withoutContext_doesNotCrash() {
        val fragment = Fragment() // No context

        // Should not crash, just log error
        fragment.toastShowLong1("Test")

        // No assertion needed - just verify no crash
    }

    // ========================================
    // Multiple Toast Tests
    // ========================================

    @Test
    fun multipleToasts_showsLatestToast() {
        context.toastShowShort1("First")
        context.toastShowShort1("Second")
        context.toastShowShort1("Third")

        assertEquals("Third", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun shortAndLongToasts_showsLatestToast() {
        context.toastShowShort1("Short")
        context.toastShowLong1("Long")

        val latestToast = ShadowToast.getLatestToast()
        assertEquals(Toast.LENGTH_LONG, latestToast.duration)
        assertEquals("Long", ShadowToast.getTextOfLatestToast())
    }
}
