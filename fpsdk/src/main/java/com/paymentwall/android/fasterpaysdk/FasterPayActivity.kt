package com.paymentwall.android.fasterpaysdk

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.content_faster_pay.*

class FasterPayActivity : AppCompatActivity() {

    companion object {
        val SUCCESS_URL_KEY = "success_url"
        val FORM_DATA_KEY = "form_data_key"

        fun callingIntent(context: Context) = Intent(context, FasterPayActivity::class.java)
    }

    private lateinit var successUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_faster_pay)

        fasterPayWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return url?.takeIf { it.startsWith(successUrl) }
                    ?.let {
                        val intent = Intent("android.intent.action.VIEW")
                        intent.data = Uri.parse(it)
                        startActivity(intent)
                        finish()
                        true
                    }
                    ?: false
            }
        }

        successUrl = intent.getStringExtra(SUCCESS_URL_KEY)
        val formData = intent.getStringExtra(FORM_DATA_KEY)
        fasterPayWebView.loadData(formData, "text/html", "UTF-8")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.d("WBFP", "change $newConfig")
    }
}
