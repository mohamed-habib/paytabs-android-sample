package com.example.paytabssample.dto

data class PaymentDetails(
    val merchantEmail: String,
    val secretKey: String,
    val language: String,
    val transactionTitle: String,
    val amount: Double,
    val currencyCode: String,
    val customerPhoneNumber: String,
    val customerEmail: String,
    val orderId: String,
    val productName: String,
    val addressBilling: String,
    val cityBilling: String,
    val stateBilling: String,
    val countryBilling: String,
    val postalCodeBilling: String,
    val shippingAddress: String,
    val cityShipping: String,
    val stateShipping: String,
    val countryShipping: String,
    val postalCodeShipping: String,
    val isTokenization: Boolean
) {

}