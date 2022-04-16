package com.example.tatarby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tatarby.databinding.FragmentEventsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Events : Fragment() {
    lateinit var events_binding: FragmentEventsBinding
    lateinit var adapter: event_adapter
    lateinit var databaseReference: DatabaseReference
    lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        events_binding = FragmentEventsBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()
        val firebaseUser = auth.currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        initRcView()
        readMessage(databaseReference)
        return events_binding.root
    }
    private fun initRcView() = with(events_binding){
        adapter = event_adapter()
        RcView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        RcView.adapter = adapter
    }
private fun readMessage(databaseReference: DatabaseReference){
    databaseReference.addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot : DataSnapshot) {
            val list = ArrayList<Event>()
            for(s in snapshot.children){
                val event = s.getValue(Event::class.java)
                if(event!=null)list.add(event)
            }
            adapter.submitList(list)
        }

        override fun onCancelled(error : DatabaseError) {
            Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
        }

    })

}
    companion object {
        @JvmStatic
        fun newInstance() = Events()
    }
}