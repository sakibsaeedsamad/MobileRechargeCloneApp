package com.sssakib.mobilerechargecloneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list_items.view.*

class ContactAdapter (items : List<Contact>, listener: OnItemClickListener):RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    private var contactList = items
    private val listener = listener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_items, parent, false)
        return ViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(contactList[position])
        }
        holder.bind(contactList[position])

    }

    fun filterList(filteredList: List<Contact>) {
        contactList = filteredList
        notifyDataSetChanged()
    }




    inner class ViewHolder(v: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(v){
       var name = v.contactNameTV
        var number = v.contactNumberTV
        fun bind(data: Contact) {
            name.text = data.name
            number.text =  data.number
        }

        }
    interface OnItemClickListener{
        fun onItemClickListener(contact: Contact)
    }

    }
