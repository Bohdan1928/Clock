package com.runner.clock

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.runner.clock.databinding.ActivitySignInBinding
import org.koin.android.ext.android.inject

class SignIn : AppCompatActivity() {
    private lateinit var rootElement: ActivitySignInBinding
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivitySignInBinding.inflate(layoutInflater)
        val view = rootElement.root
        val context = view.context
        setContentView(view)

        val intentSignUp = Intent(context, SignUp::class.java)
        val intentMain = Intent(context, MainActivity::class.java)

        val signInNameEdt = rootElement.signInNameEdt
        val signInPassword = rootElement.signInPasswordEdt
        val btnSignIn = rootElement.btnSignIn
        val tvSignUp = rootElement.tvSignUp

        tvSignUp.setOnClickListener {
            startActivity(intentSignUp)
        }
        btnSignIn.setOnClickListener {
            if (signInNameEdt.text.toString().isEmpty()
                || signInPassword.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    context,
                    "Введіть логін та пароль",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth
                    .signInWithEmailAndPassword(
                        signInNameEdt.text.toString(),
                        signInPassword.text.toString()
                    )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            context.startActivity(intentMain)
                            finish()
                        } else {
                            Toast.makeText(
                                context,
                                "Введено невірний логін або пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

}