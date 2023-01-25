package com.example.trikotaproject.ui.user.other.termsofuse.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trikotaproject.R
import com.example.trikotaproject.ui.user.other.termsofuse.model.TermsOfUseModel

class TermsOfUseAdapter(
    private val context: Context?,
    private val dataset: List<TermsOfUseModel>
): RecyclerView.Adapter<TermsOfUseAdapter.TermsOfUseCardViewHolder>(){
    class TermsOfUseCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!){
        val titleTextView: TextView? = view!!.findViewById(R.id.terms_of_use_title)
        val contentTextView: TextView? = view!!.findViewById(R.id.terms_of_use_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsOfUseCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_other_termsofuse, parent, false)
        return TermsOfUseCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TermsOfUseCardViewHolder, position: Int) {
        val termsOfUseData = dataset[position]
        holder.titleTextView?.text = termsOfUseData.title
        holder.contentTextView?.text = termsOfUseData.content
    }
}