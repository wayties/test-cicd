package com.wayties.library

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Creates and shows a short duration Toast message.<br><br>
 * 짧은 시간 Toast 메시지를 생성하고 표시합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 */
public fun Context.toastShowShort1(msg: CharSequence) {
    toastShort1(msg).show()
}

/**
 * Creates and shows a long duration Toast message.<br><br>
 * 긴 시간 Toast 메시지를 생성하고 표시합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 */
public fun Context.toastShowLong1(msg: CharSequence) {
    toastLong1(msg).show()
}

/**
 * Creates a short duration Toast without showing it.<br>
 * Allows further customization before displaying.<br><br>
 * 표시하지 않고 짧은 시간 Toast를 생성합니다.<br>
 * 표시하기 전에 추가 커스터마이징이 가능합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 *
 * @return The created Toast instance.<br><br>
 *         생성된 Toast 인스턴스.<br>
 */
public fun Context.toastShort1(msg: CharSequence): Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)

/**
 * Creates a long duration Toast without showing it.<br>
 * Allows further customization before displaying.<br><br>
 * 표시하지 않고 긴 시간 Toast를 생성합니다.<br>
 * 표시하기 전에 추가 커스터마이징이 가능합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 *
 * @return The created Toast instance.<br><br>
 *         생성된 Toast 인스턴스.<br>
 */
public fun Context.toastLong1(msg: CharSequence): Toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)

/**
 * Creates and shows a short duration Toast message from a Fragment.<br>
 * Logs an error if the Fragment's context is null.<br><br>
 * Fragment에서 짧은 시간 Toast 메시지를 생성하고 표시합니다.<br>
 * Fragment의 context가 null이면 에러를 로깅합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 */
public fun Fragment.toastShowShort1(msg: CharSequence) {
    this.context?.toastShowShort1(msg)
        ?: Log.e("toastShowShort1", "Can not Toast Show, Fragment Context is null!!")
}

/**
 * Creates and shows a long duration Toast message from a Fragment.<br>
 * Logs an error if the Fragment's context is null.<br><br>
 * Fragment에서 긴 시간 Toast 메시지를 생성하고 표시합니다.<br>
 * Fragment의 context가 null이면 에러를 로깅합니다.<br>
 *
 * @param msg The message to display in the Toast.<br><br>
 *            Toast에 표시할 메시지.<br>
 */
public fun Fragment.toastShowLong1(msg: CharSequence) {
    this.context?.toastShowLong1(msg)
        ?: Log.e("toastShowLong1", "Can not Toast Show, Fragment Context is null!!")
}
