package com.example.paytabssample.checkout

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.paytabssample.dto.PaymentDetails
import com.example.paytabssample.R
import com.example.paytabssample.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import retrofit2.Response.error

class CheckoutActivity : AppCompatActivity() {
    private val payButtonColor = "#03DAC5"
    lateinit var viewModel: CheckoutViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )
        viewModel = CheckoutViewModel(application)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.paymentDetails.observe(this, Observer { paymentDetails ->
            goPayment(paymentDetails)
        })

        viewModel.showError.observe(this, Observer {
            showError(getString(it))
        })

    }

    fun goPayment(paymentDetails: PaymentDetails) {
        val intent = Intent(applicationContext, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, paymentDetails.merchantEmail)
        intent.putExtra(PaymentParams.SECRET_KEY, paymentDetails.secretKey)
        intent.putExtra(
            PaymentParams.LANGUAGE,
            paymentDetails.language
        )
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, paymentDetails.transactionTitle)

        intent.putExtra(PaymentParams.AMOUNT, paymentDetails.amount)
        intent.putExtra(PaymentParams.CURRENCY_CODE, paymentDetails.currencyCode)

        intent.putExtra(PaymentParams.ORDER_ID, paymentDetails.orderId)
        intent.putExtra(PaymentParams.PRODUCT_NAME, paymentDetails.productName)

        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, paymentDetails.customerPhoneNumber)
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, paymentDetails.customerEmail)

        //Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, paymentDetails.addressBilling)
        intent.putExtra(PaymentParams.CITY_BILLING, paymentDetails.cityBilling)
        intent.putExtra(PaymentParams.STATE_BILLING, paymentDetails.stateBilling)
        intent.putExtra(PaymentParams.COUNTRY_BILLING, paymentDetails.countryBilling)
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            paymentDetails.postalCodeBilling
        )
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, paymentDetails.shippingAddress)
        intent.putExtra(PaymentParams.CITY_SHIPPING, paymentDetails.cityShipping)
        intent.putExtra(PaymentParams.STATE_SHIPPING, paymentDetails.stateShipping)
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, paymentDetails.countryShipping)
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            paymentDetails.postalCodeShipping
        )
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, payButtonColor)
        intent.putExtra(PaymentParams.IS_TOKENIZATION, paymentDetails.isTokenization)

        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            val responseCode = data!!.getStringExtra(PaymentParams.RESPONSE_CODE)!!
            val resultMessage = data.getStringExtra(PaymentParams.RESULT_MESSAGE)!!
            val trxnId = data.getStringExtra(PaymentParams.TRANSACTION_ID)!!
            Log.d("TAG", "responseCode: $responseCode")
            Log.d("TAG", "resultMessage: $resultMessage")

            if (responseCode == "100") goToPaymentResultScreen(resultMessage, trxnId)
            else showError(resultMessage)
        }
    }

    private fun goToPaymentResultScreen(message: String, trxnId: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage("$message \n Transaction Id: $trxnId")
            .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
            }
            .show()

    }

    private fun showError(errorMessage: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.error))
            .setMessage(errorMessage)
            .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
            }
            .show()
    }

}