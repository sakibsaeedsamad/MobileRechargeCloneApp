package com.sssakib.mobilerechargecloneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recharge_details.*

class RechargeDetailsActivity : AppCompatActivity() {
    var phoneNo:String?= null
    var operator:String?=null
    var mobileType:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_details)

        val intent = intent
        phoneNo= intent.extras!!.getString("number")
        mblNoTV.setText(phoneNo)

        //access the items of the list
        val operatorList = resources.getStringArray(R.array.operatorAarray)
//access the operatorSpinner
        if (mblOperatorSpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operatorList)
            mblOperatorSpinner.adapter = adapter

            mblOperatorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    operator = operatorList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
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
            val intent = Intent(this,ConfirmationActivity::class.java)
            intent.putExtra("number", phoneNo)
            intent.putExtra("operator",operator)
            intent.putExtra("amount",amount)
            intent.putExtra("mobileType",mobileType)

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