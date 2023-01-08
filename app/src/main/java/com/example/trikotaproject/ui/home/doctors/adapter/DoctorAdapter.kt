package com.example.trikotaproject.ui.home.doctors.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trikotaproject.R
import com.example.trikotaproject.ui.home.doctors.data.DoctorDatasource
import com.example.trikotaproject.ui.home.doctors.model.DoctorModel

class DoctorAdapter(
    private val context: Context?,
    private val dataset: List<DoctorModel>
): RecyclerView.Adapter<DoctorAdapter.DoctorCardViewHolder>(){

    private val doctorsList = DoctorDatasource.doctors

    class DoctorCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!){
        val doctorNameTextView: TextView? = view!!.findViewById(R.id.item_home_doctors_name)
        val doctorOccupationTextView: TextView? = view!!.findViewById(R.id.item_home_doctors_occupation)
        val doctorExperienceTextView: TextView? = view!!.findViewById(R.id.item_home_doctors_experience)
        val doctorHospitalTextView: TextView? = view!!.findViewById(R.id.item_home_doctors_hospital)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_doctors, parent, false)
        return DoctorCardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        val doctorData = dataset[position]
        holder.doctorNameTextView?.text = doctorData.name
        holder.doctorOccupationTextView?.text = doctorData.occupation
        holder.doctorExperienceTextView?.text = "Experience: ${doctorData.experience}"
        holder.doctorHospitalTextView?.text = doctorData.hospital
    }

    override fun getItemCount(): Int {
        return doctorsList.size
    }
}