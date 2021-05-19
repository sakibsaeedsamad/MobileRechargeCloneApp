package com.sssakib.mobilerechargecloneapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recharge_details.*

class RechargeDetailsActivity : AppCompatActivity() {
    var phoneNo: String? = null
    var operator: String? = null
    var mobileType: String? = null
    var compareValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_details)

        val intent = intent
        phoneNo = intent.extras!!.getString("number")
        mblNoTV.setText(phoneNo)

        if (phoneNo!!.subSequence(0, 3).equals("016")) {
            compareValue = "Airtel"
        }
        if (phoneNo!!.subSequence(0, 3).equals("019") || phoneNo!!.subSequence(0, 3)
                .equals("014")
        ) {
            compareValue = "Banglalink"
        }
        if (phoneNo!!.subSequence(0, 3).equals("017") || phoneNo!!.subSequence(0, 3)
                .equals("013")
        ) {
            compareValue = "Grameenphone"

        }
        if (phoneNo!!.subSequence(0, 3).equals("015")) {
            compareValue = "Teletalk"

        }
        if (phoneNo!!.subSequence(0, 3).equals("018")) {
            compareValue = "Robi"

        }

        //access the items of the list
        val operatorList = resources.getStringArray(R.array.operatorAarray)
        // Create an ArrayAdapter
        val operatorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operatorList)
        // Specify the layout to use when the list of choices appears
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        mblOperatorSpinner.adapter = operatorAdapter

        if (compareValue != null) {
            val spinnerPosition: Int = operatorAdapter.getPosition(compareValue)
            mblOperatorSpinner.setSelection(spinnerPosition)
        }
        mblOperatorSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    operator = operatorList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }


        //access the items of the list
        val typeList = resources.getStringArray(R.array.mobileTypeAarray)
//access the typeSpinner
        if (mblTypeSpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList)
            mblTypeSpinner.adapter = adapter

            mblTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mobileType = typeList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        enterAmountBTN.setOnClickListener {
            val amount = enterAmountET.getText().toString().trim()
            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra("number", phoneNo)
            intent.putExtra("operator", operator)
            intent.putExtra("amount", amount)
            intent.putExtra("mobileType", mobileType)

            startActivity(intent)
        }



        fiftyTKBTN.setOnClickListener {
            enterAmountET.setText("50")
        }
        hundredTKBTN.setOnClickListener {
            enterAmountET.setText("100")
        }
        onefiftyTKBTN.setOnClickListener {
            enterAmountET.setText("150")
        }
        twohundredTKBTN.setOnClickListener {
            enterAmountET.setText("200")
        }


    }


}