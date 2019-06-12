package com.paymentwall.android.fasterpaysdk

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class FasterPayWebView: WebView {
    constructor(context: Context): super(context) {
        init()
    }
    constructor(context: Context, attributes: AttributeSet): super(context, attributes) {
        init()
    }
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int): super(context, attributes, defStyleAttr) {
        init()
    }

    private fun init() {
        settings.javaScriptEnabled = true
        settings.loadsImagesAutomatically = true
        settings.domStorageEnabled = true
    }

    override fun onCheckIsTextEditor(): Boolean = true
}