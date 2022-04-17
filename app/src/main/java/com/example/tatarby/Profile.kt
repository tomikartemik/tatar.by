package com.example.tatarby

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatarby.databinding.FragmentProfileBinding



class Profile : Fragment() {
    lateinit var profile_binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        profile_binding = FragmentProfileBinding.inflate(inflater)
        profile_binding.textView5.text = pref.getString("Name" , "")
        profile_binding.imageButton.setOnClickListener {
            val creator = pref.edit()
            creator.remove("Name")
            creator.remove("UserId")
            creator.apply()
            startActivity(Intent(this.context , Login::class.java))
            activity?.finish()
        }
        return profile_binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = Profile()
    }
}