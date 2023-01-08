package com.example.trikotaproject.ui.other

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trikotaproject.R
import com.example.trikotaproject.contract.navigator
import com.example.trikotaproject.databinding.FragmentOtherBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.exit

class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.myProfileBtn.setOnClickListener {
            navigator().showMyProfile()
        }
        binding.logoutBtn.setOnClickListener {
            navigator().logOut()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun alertShowSocialNetworks(){
        val dialog = AlertDialog.Builder(context)
            .setCancelable(true)
            .setTitle(R.string.social_networks)

    }
}