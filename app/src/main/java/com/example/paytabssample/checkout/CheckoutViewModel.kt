package com.example.paytabssample.checkout

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.paytabssample.dto.Address
import com.example.paytabssample.Keys
import com.example.paytabssample.dto.PaymentDetails
import com.example.paytabssample.R
import com.paytabs.paytabs_sdk.utils.PaymentParams
import java.util.*

class CheckoutViewModel(app: Application) : AndroidViewModel(app) {

    val showError = MutableLiveData<Int>()
    val paymentDetails = MutableLiveData<PaymentDetails>()
    val transactionTitle = "Transaction Title"
    val productName = "16GB RAM"
    val orderId = "123412"
    val amount = 5.0
    val currencyCode = "BHD"

    val merchantEmail = Keys.readMerchantEmail()
    val secretKey = Keys.readSecretKey()
    val language = Locale.getDefault().language

    val isTokenization = true

    val customerEmail = MutableLiveData<String>()
    val customerPhoneNumber = MutableLiveData<String>()

    val billingAddress = Address()
    val shippingAddress = Address()

    fun doCheckout(view: View) {

        val customerPhoneNumber = customerPhoneNumber.value.orEmpty()
        val customerEmail = customerEmail.value.orEmpty()

        if (isValidPhoneNumber(customerPhoneNumber) &&
            isValidEmail(customerEmail) &&
            isValidAddress(billingAddress) &&
            isValidAddress(shippingAddress)
        ) {
            paymentDetails.value = PaymentDetails(
                merchantEmail,
                secretKey,
                language,
                transactionTitle,
                amount,
                currencyCode,
                customerPhoneNumber,
                customerEmail,
                orderId,
                productName,
                billingAddress.address,
                billingAddress.city,
                billingAddress.state,
                billingAddress.country,
                billingAddress.postalCode,
                shippingAddress.address,
                shippingAddress.city,
                shippingAddress.state,
                shippingAddress.country,
                shippingAddress.postalCode,
                isTokenization
            )
        }

    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val isValid =
            phoneNumber.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneNumber).matches()
        if (!isValid) showError.value = R.string.please_enter_valid_phone_number

        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        val isValid =
            email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isValid) showError.value = R.string.please_enter_valid_email_address
        return isValid
    }

    private fun isValidAddress(address: Address): Boolean {
        val isValid = address.address.isNotEmpty() && address.city.isNotEmpty() &&
                address.country.isNotEmpty() && address.postalCode.isNotEmpty()

        if (!isValid) showError.value = R.string.please_fill_all_address_fields

        return isValid
    }
}