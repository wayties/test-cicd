package com.wayties.test_cicd

import android.os.Bundle
import android.widget.Button
import com.wayties.library.toastShowShort1
import kr.open.library.simple_ui.xml.ui.activity.BaseActivity

/**
 * 주석 추가  -> Github Push 에서 Dooray 메신저 알람을 위한 테스트 주석
 */
class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.btnTest01).setOnClickListener { toastShowShort1("Hello, World!") }
    }
}
