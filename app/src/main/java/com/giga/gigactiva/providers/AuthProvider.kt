package com.giga.gigactiva.providers

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthProvider {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password:String): Task<AuthResult>{
        return auth.signInWithEmailAndPassword(email, password)
    }
    fun existSession(): Boolean{
        var exist = false
        if (auth.currentUser != null){
            exist = true
        }
        return exist
    }
}