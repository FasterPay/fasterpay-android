package com.paymentwall.android.fasterpaysdk

import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_DURATION_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_TRIAL_AMOUNT_FIELD
import com.paymentwall.android.fasterpaysdk.FormConverter.Companion.FORM_RECURRING_TRIAL_PERIOD_FIELD

class SubscriptionsForm(
    parameters: MutableMap<String, String>,
    apiUrl: String) : Form(parameters, apiUrl) {

    fun recurringTrialAmount(recurring_trial_amount: String): SubscriptionsForm {
        parameters[FORM_RECURRING_TRIAL_AMOUNT_FIELD] = recurring_trial_amount
        return this
    }

    fun recurringTrialPeriod(recurring_trial_period: String): SubscriptionsForm {
        parameters[FORM_RECURRING_TRIAL_PERIOD_FIELD] = recurring_trial_period
        return this
    }

    fun recurringDuration(recurring_duration: String): SubscriptionsForm {
        parameters[FORM_RECURRING_DURATION_FIELD] = recurring_duration
        return this
    }
}

interface RequireRecurringName<T> {
    fun recurringName(recurring_name: String): RequireRecurringSkuId<T>
}

interface RequireRecurringSkuId<T> {
    fun recurringSkuId(recurring_sku_id: String): RequiredRecurringPeriod<T>
}

interface RequiredRecurringPeriod<T> {
    fun recurringPeriod(recurring_period: String): T
}