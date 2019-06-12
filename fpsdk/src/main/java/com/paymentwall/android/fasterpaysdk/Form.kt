package com.paymentwall.android.fasterpaysdk

import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_CITY_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_EMAIL_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_FIRST_NAME_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_LAST_NAME_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_PINGBACK_URL
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_ZIP_FIELD

open class Form internal constructor(
    val parameters: MutableMap<String, String>,
    private val apiUrl: String) {

    fun email(email: String): Form {
        parameters[FORM_EMAIL_FIELD] = email
        return this
    }

    fun firstName(first_name: String): Form {
        parameters[FORM_FIRST_NAME_FIELD] = first_name
        return this
    }

    fun lastName(last_name: String): Form {
        parameters[FORM_LAST_NAME_FIELD] = last_name
        return this
    }

    fun city(city: String): Form {
        parameters[FORM_CITY_FIELD] = city
        return this
    }

    fun zip(zip: String): Form {
        parameters[FORM_ZIP_FIELD] = zip
        return this
    }

    fun pingback_url(pingback_url: String): Form {
        parameters[FORM_PINGBACK_URL] = pingback_url
        return this
    }

    internal fun build(): String {
        return buildForm()
    }

    private fun buildForm(): String {
        val htmlForm = StringBuilder()
        htmlForm.append("<form align=\"center\" method=\"post\" action=\"${apiUrl}/payment/form\">")

        parameters.map { "<input type=\"hidden\" name=\"${it.key}\" value=\"${it.value}\">" }
            .joinTo(htmlForm, "")

        htmlForm.append("<input type=\"Submit\" value=\"Pay Now\" id=\"fasterpay-submit\" style=\"display:none\"/></form>")

        htmlForm.append("<script type=\"text/javascript\">document.getElementById(\"fasterpay-submit\").click(); </script>")

        return htmlForm.toString()
    }
}

interface RequireAmount<T> {
    fun amount(amount: String): RequireCurrency<T>
}

interface RequireCurrency<T> {
    fun currency(currency: String): RequireDescription<T>
}

interface RequireDescription<T> {
    fun description(description: String): RequireMerchantOrderId<T>
}

interface RequireMerchantOrderId<T> {
    fun merchantOrderId(merchant_order_id: String): RequireSuccessUrl<T>
}

interface RequireSuccessUrl<T> {
    fun successUrl(success_url: String): T
}