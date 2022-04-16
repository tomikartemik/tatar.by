package com.example.tatarby

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tatarby.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

lateinit var bin: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)

        supportFragmentManager.beginTransaction().replace(R.id.frame, Events.newInstance()).commit()
        bin.btmnav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.events -> supportFragmentManager.beginTransaction().replace(
                    R.id.frame,
                    Events.newInstance()
                ).commit()
                R.id.create -> supportFragmentManager.beginTransaction().replace(
                    R.id.frame,
                    CreateEvent.newInstance()
                ).commit()
                R.id.profile -> supportFragmentManager.beginTransaction().replace(
                    R.id.frame,
                    Profile.newInstance()
                ).commit()
            }
            true
        }
    }
}