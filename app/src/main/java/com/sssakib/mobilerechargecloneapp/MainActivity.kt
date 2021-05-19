package com.sssakib.mobilerechargecloneapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ContactAdapter.OnItemClickListener,
    RecentContactAdapter.OnRecentItemClickListener {


    private var model: MutableList<Contact>? = null
    private var adapter: ContactAdapter? = null

    private var rCModel: MutableList<Contact>? = null
    private var rCAdapter: RecentContactAdapter? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestContactPermission()

        val contactDao = ContactDatabase.getDatabase(getApplication())?.contactDao()
        rCModel = contactDao?.getAllContactInfo()

        adapter = ContactAdapter(model!!, this)
        adapter?.notifyDataSetChanged()
        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        contactRecyclerView.adapter = adapter

        rCAdapter = RecentContactAdapter(rCModel!!, this)
        rCAdapter?.notifyDataSetChanged()
        recentContactRecyclerView.layoutManager = LinearLayoutManager(this)
        recentContactRecyclerView.adapter = rCAdapter




        goToDetailsBTN.setOnClickListener {
            val uNumber = phoneNumberOrNameET.getText().toString().trim()
            val intent = Intent(this, RechargeDetailsActivity::class.java)
            intent.putExtra("number", uNumber)
            startActivity(intent)

        }
        phoneNumberOrNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                adapter?.filter?.filter(s)
                rCAdapter?.filter?.filter(s)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

    }


    private fun requestContactPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    // permission is granted
                    model = getContacts()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun getContacts(): MutableList<Contact>? {
        val contactList: MutableList<Contact> = ArrayList()
        var no: String? = null
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            if (number.length == 11) {
                no = number
            }
            if (number.length == 14) {
                no = number.subSequence(3, 14).toString()
            } else {
                no = number
            }

            val obj = Contact(0, name, no)

            contactList.add(obj)
        }

        return contactList
        contacts.close()
    }


    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                openSettings()
            })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }


    override fun onItemClickListener(contact: Contact) {

        val contactDao = ContactDatabase.getDatabase(getApplication())?.contactDao()

        val uName = contact.name
        val uNumber = contact.number


        phoneNumberOrNameET.setText(uNumber)

        if (phoneNumberOrNameET.getText().toString().trim().length == 11) {
            val intent = Intent(this, RechargeDetailsActivity::class.java)
            intent.putExtra("number", uNumber)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select 11 digit Number!", Toast.LENGTH_SHORT).show()
        }

        if(rCModel!!.size < 5)
        {
            contactDao?.insertContact(contact)
        }
        else{

            for(i in rCModel!!.indices) {
                if (rCModel!![i].number == uNumber)
                {
                 contactDao?.updateContact(rCModel!![i].id,uName,uNumber)
                }
                else{
                    contactDao?.updateContact(rCModel!![4].id,uName,uNumber)
                }
            }

        }

    }

    override fun onRecentItemClickListener(contact: Contact) {
        val uName = contact.name
        val uNumber = contact.number


        phoneNumberOrNameET.setText(uNumber)

        if (phoneNumberOrNameET.getText().toString().trim().length == 11) {
            val intent = Intent(this, RechargeDetailsActivity::class.java)
            intent.putExtra("number", uNumber)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select 11 digit Number!", Toast.LENGTH_SHORT).show()
        }

    }


}