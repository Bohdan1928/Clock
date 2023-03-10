package com.runner.clock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.runner.clock.data.database.firebase.FirebaseAuthService
import com.runner.clock.databinding.ActivitySignUpBinding
import com.runner.clock.objects.User
import org.koin.android.ext.android.inject

class SignUp : AppCompatActivity() {
    private lateinit var rootElement: ActivitySignUpBinding
    private val database: DatabaseReference by inject()
    private var auth: FirebaseAuthService = FirebaseAuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivitySignUpBinding.inflate(layoutInflater)
        val view = rootElement.root
        val context = view.context
        setContentView(view)

        val editTextNameOfUser = rootElement.nameOfUserEdt
        val editTextEmail = rootElement.emailEdt
        val editTextPhoneNumber = rootElement.phoneNumberEdt
        val editTextPassword = rootElement.passwordEdt
        val editTextRepeatPassword = rootElement.repeatPasswordEdt
        val buttonSignUp = rootElement.singUpBtn


        buttonSignUp.setOnClickListener {
            when {
                editTextNameOfUser.text.isEmpty() ->
                    Toast.makeText(
                        context,
                        "Введіть ім'я",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextEmail.text.isEmpty() ->
                    Toast.makeText(
                        context,
                        "Введіть email",
                        Toast.LENGTH_SHORT
                    ).show()
                !(editTextEmail.text.toString().contains("@gmail.com")) ->
                    Toast.makeText(
                        context,
                        "Введіть коректну поштову андресу",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPhoneNumber.text.isEmpty() ->
                    Toast.makeText(
                        context,
                        "Введіть номер телефону",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextRepeatPassword.text.isEmpty() ->
                    Toast.makeText(
                        context,
                        "Введіть пароль повторно",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPassword.text.isEmpty() ->
                    Toast.makeText(
                        context,
                        "Введіть пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPassword.text.toString() != editTextRepeatPassword.text.toString() ->
                    Toast.makeText(
                        context,
                        "Введені паролі не збігаються",
                        Toast.LENGTH_SHORT
                    ).show()
                (editTextNameOfUser.text.isNotEmpty() &&
                        editTextEmail.text.isNotEmpty() &&
                        editTextNameOfUser.text.isNotEmpty() &&
                        editTextPassword.text.isNotEmpty() &&
                        editTextRepeatPassword.text.toString() == editTextPassword.text.toString()) -> {
                    val user = User(
                        editTextNameOfUser.text.toString(),
                        editTextEmail.text.toString(),
                        editTextPhoneNumber.text.toString(),
                        editTextPassword.text.toString()
                    )
                    database.child("Users").push().setValue(user)
                    Log.d("SingUp", "User added to db")
                    auth.singUp(user.email.toString(), user.password.toString())
                    Log.d("SingUp", "User sing up")

                    editTextNameOfUser.text = null
                    editTextEmail.text = null
                    editTextPhoneNumber.text = null
                    editTextPassword.text = null
                    editTextRepeatPassword.text = null

                    val intent = Intent(context, SignIn::class.java)
                    context.startActivity(intent)
                }

            }
        }
    }
}