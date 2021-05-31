package com.sssakib.mobilerechargecloneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.activity_recharge_details.*

class ConfirmationActivity : AppCompatActivity() {
    var phoneNo:String?= null
    var operator:String?=null
    var amount:String?= null
    var mobileType:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        val intent = intent
        phoneNo= intent.extras!!.getString("number")
        operator= intent.extras!!.getString("operator")
        amount= intent.extras!!.getString("amount")
        mobileType= intent.extras!!.getString("mobileType")
        confirmMblNoTV.setText(phoneNo)
        confirmMblOperatorTV.setText(operator)
        amountTV.setText(amount)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, RechargeDetailsActivity::class.java))
        finish()
    }
}