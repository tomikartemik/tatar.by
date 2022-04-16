package com.example.tatarby

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tatarby.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var binding: ActivityLoginBinding
lateinit var auth : FirebaseAuth
lateinit var databaseReference: DatabaseReference
lateinit var pref: SharedPreferences

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("user_info", MODE_PRIVATE)
        if (pref.getString("Name", null)?.toString() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val ename = binding.name.text.toString()
            val epass = binding.pass.text.toString()
            if (ename == "") {
                Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT).show()
            }
            if (epass == "") {
                Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT).show()
            } else {
                //database(ename)
                //addUser(ename)
                //startActivity(Intent(this , HomeActivity::class.java))
                auth.createUserWithEmailAndPassword(ename + "@gmail.com", epass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val userId: String = currentUser!!.uid
                            databaseReference =
                                FirebaseDatabase.getInstance().getReference("Users").child(userId)
                            var hashMap: HashMap<String, String> = HashMap()
                            hashMap.put("userId", userId)
                            hashMap.put("username", ename)
                            databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    val creator = pref.edit()
                                    creator.putString("Name", ename)
                                    creator.putString("UserId", userId)
                                    creator.apply()
                                    Log.d("Name", pref.getString("Name", null).toString())
                                    Log.d("UserId", pref.getString("UserId", null).toString())
                                    finish()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
        binding.regBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val ename = binding.name.text.toString()
            val epass = binding.pass.text.toString()
            if (ename == "") {
                Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT).show()
            }
            if (epass == "") {
                Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(ename + "@gmail.com", epass)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success")
                            startActivity(Intent(this, MainActivity::class.java))
                            val currentUser = auth.currentUser
                            val userId: String = currentUser!!.uid
                            val creator = pref.edit()
                            creator.putString("Name", ename)
                            creator.putString("UserId", userId)
                            creator.apply()
                            Log.d("Name", pref.getString("Name", null).toString())
                            Log.d("UserId", pref.getString("UserId", null).toString())
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            )  .show()
                        }
                    }
            }
        }
    }
}