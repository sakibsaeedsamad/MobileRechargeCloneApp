package com.sssakib.mobilerechargecloneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list_items.view.*
import kotlinx.android.synthetic.main.recent_contact_list_items.view.*
import java.util.ArrayList

class RecentContactAdapter (items : MutableList<Contact>, listener: OnRecentItemClickListener): RecyclerView.Adapter<RecentContactAdapter.ViewHolder>(),
    Filterable {

    private lateinit var contactList: List<Contact>
    private lateinit var oldContactList: List<Contact>

    private val listener = listener

    inner class ViewHolder(v: View, listener: OnRecentItemClickListener) : RecyclerView.ViewHolder(v){

        var name = v.recentContactNameTV
        var number = v.recentContactNumberTV
        fun bind(data: Contact) {
            name.text = data.name
            number.text =  data.number
        }

    }
    init {
        contactList = items
        oldContactList = contactList
        notifyDataSetChanged()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_contact_list_items, parent, false)
        return ViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return contactList.size
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onRecentItemClickListener(contactList[position])
        }
        holder.bind(contactList[position])

    }

    interface OnRecentItemClickListener{
        fun onRecentItemClickListener(contact: Contact)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.toString().isEmpty()) {
                    contactList = oldContactList
                } else {


                    val resultList = ArrayList<Contact>()
                    for (row in oldContactList) {
                        if (
                            row.number.toString().toLowerCase()
                                ?.contains(constraint.toString().toLowerCase()) or
                            row.name.toString().toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    contactList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactList = results?.values as ArrayList<Contact>
                notifyDataSetChanged()
            }

        }
    }
}
