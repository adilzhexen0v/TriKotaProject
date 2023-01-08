package com.example.trikotaproject.ui.home.hospitals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trikotaproject.R
import com.example.trikotaproject.ui.home.hospitals.model.HospitalModel

class HospitalAdapter(
    private val context: Context?,
    private val dataset: List<HospitalModel>
): RecyclerView.Adapter<HospitalAdapter.HospitalCardViewHolder>(){
    class HospitalCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!){
        val hospitalImageView: ImageView? = view!!.findViewById(R.id.item_home_hospital_image)
        val hospitalNameTextView: TextView? = view!!.findViewById(R.id.item_home_hospital_hospital)
        val hospitalCityTextView: TextView? = view!!.findViewById(R.id.item_home_hospital_city)
        val hospitalAddressTextView: TextView? = view!!.findViewById(R.id.item_home_hospital_address)
        val hospitalTimeTextView: TextView? = view!!.findViewById(R.id.item_home_hospital_time)
        val hospitalPhoneTextView: TextView? = view!!.findViewById(R.id.item_home_hospital_phone)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_hospital, parent, false)
        return HospitalCardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: HospitalCardViewHolder, position: Int) {
        val hospitalData = dataset[position]
        holder.hospitalImageView?.setImageResource(hospitalData.image)
        holder.hospitalNameTextView?.text = hospitalData.hospital
        holder.hospitalCityTextView?.text = hospitalData.city
        holder.hospitalAddressTextView?.text = hospitalData.address
        holder.hospitalTimeTextView?.text = hospitalData.time
        holder.hospitalPhoneTextView?.text = hospitalData.phone
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}