package com.wayties.library

import android.content.Context
import android.widget.Toast

public fun Context.toastShowShort1(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
public fun Context.toastShowLong1(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()