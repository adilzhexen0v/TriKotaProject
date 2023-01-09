package com.example.trikotaproject.ui.other.termsofuse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentOtherTermsofuseBinding
import com.example.trikotaproject.ui.other.termsofuse.adapter.TermsOfUseAdapter
import com.example.trikotaproject.ui.other.termsofuse.data.TermsOfUseDatasource

class TermsOfUseFragment: Fragment() {
    private lateinit var binding: FragmentOtherTermsofuseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherTermsofuseBinding.inflate(inflater, container, false)
        val termsOfUseRecyclerView = binding.recyclerView
        termsOfUseRecyclerView.adapter = TermsOfUseAdapter(context, TermsOfUseDatasource.terms)
        return binding.root
    }
}