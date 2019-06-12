package com.paymentwall.android.fasterpaysdk

import android.content.Context
import android.content.Intent
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_AMOUNT_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_API_KEY_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_CURRENCY_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_DESCRIPTION_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_MERCHANT_ORDER_ID_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_NAME_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_PERIOD_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_SKU_ID_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_SUCCESS_URL_FIELD

class FasterPay(
    private val publicKey: String,
    private val isTest: Boolean = false
) {

    fun form() =
        object : RequireAmount<Form> {
            override fun amount(amount: String) = object : RequireCurrency<Form> {
                override fun currency(currency: String) = object : RequireDescription<Form> {
                    override fun description(description: String) =
                        object : RequireMerchantOrderId<Form> {
                            override fun merchantOrderId(merchant_order_id: String) =
                                object : RequireSuccessUrl<Form> {
                                    override fun successUrl(success_url: String): Form {
                                        val parameters = HashMap<String, String>()
                                        parameters[FORM_AMOUNT_FIELD] = amount
                                        parameters[FORM_CURRENCY_FIELD] = currency
                                        parameters[FORM_DESCRIPTION_FIELD] = description
                                        parameters[FORM_MERCHANT_ORDER_ID_FIELD] = merchant_order_id
                                        parameters[FORM_SUCCESS_URL_FIELD] = success_url
                                        parameters[FORM_API_KEY_FIELD] = publicKey
                                        return Form(parameters, getApiUrl())
                                    }
                                }
                        }
                }
            }
        }

    fun subscription() =
        object : RequireAmount<RequireRecurringName<SubscriptionsForm>> {
            override fun amount(amount: String) =
                object : RequireCurrency<RequireRecurringName<SubscriptionsForm>> {
                    override fun currency(currency: String) =
                        object : RequireDescription<RequireRecurringName<SubscriptionsForm>> {
                            override fun description(description: String) =
                                object : RequireMerchantOrderId<RequireRecurringName<SubscriptionsForm>> {
                                    override fun merchantOrderId(merchant_order_id: String) =
                                        object : RequireSuccessUrl<RequireRecurringName<SubscriptionsForm>> {
                                            override fun successUrl(success_url: String) =
                                                object : RequireRecurringName<SubscriptionsForm> {
                                                    override fun recurringName(recurring_name: String) =
                                                        object : RequireRecurringSkuId<SubscriptionsForm> {
                                                            override fun recurringSkuId(recurring_sku_id: String) =
                                                                object : RequiredRecurringPeriod<SubscriptionsForm> {
                                                                    override fun recurringPeriod(recurring_period: String): SubscriptionsForm {
                                                                        val parameters = HashMap<String, String>()
                                                                        parameters[FORM_AMOUNT_FIELD] = amount
                                                                        parameters[FORM_CURRENCY_FIELD] = currency
                                                                        parameters[FORM_DESCRIPTION_FIELD] = description
                                                                        parameters[FORM_MERCHANT_ORDER_ID_FIELD] = merchant_order_id
                                                                        parameters[FORM_SUCCESS_URL_FIELD] = success_url
                                                                        parameters[FORM_API_KEY_FIELD] = publicKey
                                                                        parameters[FORM_RECURRING_NAME_FIELD] = recurring_name
                                                                        parameters[FORM_RECURRING_SKU_ID_FIELD] = recurring_sku_id
                                                                        parameters[FORM_RECURRING_PERIOD_FIELD] = recurring_period
                                                                        return SubscriptionsForm(
                                                                            parameters,
                                                                            getApiUrl()
                                                                        )
                                                                    }
                                                                }
                                                        }
                                                }

                                        }

                                }

                        }
                }
        }

    fun prepareCheckout(context: Context, form: Form): Intent {
        val prepareIntent = FasterPayActivity.callingIntent(context)
        prepareIntent.putExtra(FasterPayActivity.SUCCESS_URL_KEY, form.parameters[FORM_SUCCESS_URL_FIELD])
        prepareIntent.putExtra(FasterPayActivity.FORM_DATA_KEY, form.build())
        return prepareIntent
    }

    fun getApiUrl(): String {
        return if (isTest) {
            "https://pay.sandbox.fasterpay.com"
        } else {
            "https://pay.fasterpay.com"
        }
    }
}