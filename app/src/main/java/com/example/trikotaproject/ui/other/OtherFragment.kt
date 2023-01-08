package com.example.trikotaproject.ui.other

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.contract.navigator
import com.example.trikotaproject.databinding.DialogSocialNetworksBinding
import com.example.trikotaproject.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private lateinit var dialogBinding: DialogSocialNetworksBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(inflater, container, false)
        dialogBinding = DialogSocialNetworksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dialogBinding.youtubeBtn.setOnClickListener {
            openLink("https://www.youtube.com/", container!!.context)
        }
        dialogBinding.telegramBtn.setOnClickListener {
            openLink("https://web.telegram.org/", container!!.context)
        }
        dialogBinding.facebookBtn.setOnClickListener {
            openLink("https://www.facebook.com/index.php", container!!.context)
        }
        dialogBinding.instagramBtn.setOnClickListener {
            openLink("https://www.instagram.com/", container!!.context)
        }
        dialogBinding.linkedinBtn.setOnClickListener {
            openLink("https://kz.linkedin.com/", container!!.context)
        }

        binding.myProfileBtn.setOnClickListener {
            navigator().showMyProfile()
        }
        binding.logoutBtn.setOnClickListener {
            navigator().logOut()
        }
        binding.socialNetworksBtn.setOnClickListener {
            alertShowSocialNetworks()
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
            .setView(dialogBinding.root)
            .setNegativeButton("Close") {_, _ ->
                navigator().showOtherPage()
            }
            .create()
        dialog.show()
    }
}