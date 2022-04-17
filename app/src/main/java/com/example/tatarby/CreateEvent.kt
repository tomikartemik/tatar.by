package com.example.tatarby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tatarby.databinding.FragmentCreateEventBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CreateEvent : Fragment() {
    lateinit var create_binding: FragmentCreateEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        create_binding = FragmentCreateEventBinding.inflate(inflater)
        create_binding.button.setOnClickListener {
            val event = create_binding.eventEt.text.toString()
            val date = create_binding.dateEt.text.toString()
            val time = create_binding.timeEt.text.toString()
            val text = create_binding.textEt.text.toString()
            if(text != "" && time != "" && date != "" && event != ""){
                val currentDate = Date()
                val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val dateText: String = dateFormat.format(currentDate)

                if((date.substring(0, 2).toInt() >= dateText.substring(0, 2).toInt()
                    && date.substring(3, 5).toInt() >= dateText.substring(3, 5).toInt()
                    && date.substring(6, 10).toInt() >= dateText.substring(6, 10).toInt())
                    ||(date.substring(3, 5).toInt() > dateText.substring(3, 5).toInt()
                            && date.substring(6, 10).toInt() >= dateText.substring(6, 10).toInt())
                    ||(date.substring(6, 10).toInt() > dateText.substring(6, 10).toInt())){
                    createEvent(event, date, time, text)
                }
                create_binding.eventEt.setText("")
                create_binding.dateEt.setText("")
                create_binding.timeEt.setText("")
                create_binding.textEt.setText("")
            }else{
                Toast.makeText(activity?.applicationContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        return create_binding.root
    }

    private fun createEvent(event: String, date: String, time: String, text: String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("event", event)
        hashMap.put("date", date)
        hashMap.put("time", time)
        hashMap.put("text", text)

        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        hashMap.put("count", (0..200).random().toString())
        //hashMap.put("count", "0")
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        reference!!.child("Events").push().setValue(hashMap)

    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateEvent()
    }
}