package com.giga.gigactiva.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.giga.gigactiva.databinding.ActivityMainBinding
import com.giga.gigactiva.providers.AuthProvider
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val authProvider = AuthProvider()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

       binding.btLogin.setOnClickListener { login() }
    }
    private fun login(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (isValidForm(email, password)){

          authProvider.login(email, password).addOnCompleteListener {
              if (it.isSuccessful){
                  goToHome()
              }
              else{
                  Toast.makeText(this@MainActivity, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                  Log.d("FIREBASE", "ERROR: ${it.exception.toString()}")
              }
          }

        }

    }
    private fun goToHome(){
        val i = Intent(this@MainActivity, HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }
    private fun isValidForm(email:String, password:String):Boolean{
       if (email.isEmpty()){
           Toast.makeText(this, "debes ingresar un correo electrónico válido", Toast.LENGTH_SHORT).show()
           return false
       }
        if (password.isEmpty()){
            Toast.makeText(this, "debes ingresar una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
       
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.existSession()){
            goToHome()
        }
    }


}