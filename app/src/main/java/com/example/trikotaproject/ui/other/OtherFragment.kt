package com.example.trikotaproject.ui.other

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.DialogSocialNetworksBinding
import com.example.trikotaproject.databinding.DialogTechSupportBinding
import com.example.trikotaproject.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private lateinit var dialogSocialNetworksBinding: DialogSocialNetworksBinding
    private lateinit var dialogTechSupportBinding: DialogTechSupportBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(inflater, container, false)
        dialogSocialNetworksBinding = DialogSocialNetworksBinding.inflate(inflater, container, false)
        dialogTechSupportBinding = DialogTechSupportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dialogSocialNetworksBinding.youtubeBtn.setOnClickListener {
            openLink("https://www.youtube.com/", container!!.context)
        }
        dialogSocialNetworksBinding.telegramBtn.setOnClickListener {
            openLink("https://web.telegram.org/", container!!.context)
        }
        dialogSocialNetworksBinding.facebookBtn.setOnClickListener {
            openLink("https://www.facebook.com/index.php", container!!.context)
        }
        dialogSocialNetworksBinding.instagramBtn.setOnClickListener {
            openLink("https://www.instagram.com/", container!!.context)
        }
        dialogSocialNetworksBinding.linkedinBtn.setOnClickListener {
            openLink("https://kz.linkedin.com/", container!!.context)
        }

        binding.myProfileBtn.setOnClickListener {
            navigator().showMyProfile()
        }
        binding.socialNetworksBtn.setOnClickListener {
            alertShowSocialNetworks()
        }
        binding.techSupportBtn.setOnClickListener {
            alertShowTechSupport()
        }
        binding.termsOfUseBtn.setOnClickListener {
            navigator().showTermsOfUse()
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

    private fun openLink(link: String, context: Context) {
        val queryUrl:Uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        context.startActivity(intent)
    }

    private fun alertShowSocialNetworks(){
        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.social_networks)
            .setView(dialogSocialNetworksBinding.root)
            .setNegativeButton("Close") {_, _ ->
                navigator().showOtherPage()
            }
            .create()
        dialog.show()
    }

    private fun alertShowTechSupport(){
        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.tech_support)
            .setView(dialogTechSupportBinding.root)
            .setPositiveButton("Send") {_, _ ->
                Toast.makeText(context, R.string.tech_support_send_message, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close") {_, _ ->
                navigator().showOtherPage()
            }
            .create()
        dialog.show()
    }
}