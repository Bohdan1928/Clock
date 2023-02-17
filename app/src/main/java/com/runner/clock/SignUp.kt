package com.runner.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.runner.clock.database.firebase.FirebaseAuthService
import com.runner.clock.database.firebase.FirebaseDatabaseService
import com.runner.clock.database.firebase.firebaseModule
import com.runner.clock.databinding.ActivitySignUpBinding
import com.runner.clock.objects.User
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SignUp : AppCompatActivity() {
    private lateinit var rootElement: ActivitySignUpBinding
    private val database by inject<FirebaseDatabaseService>()
    private val auth by inject<FirebaseAuthService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivitySignUpBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        startKoin {
            androidContext(this@SignUp)
            modules(firebaseModule)
        }

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
                        rootElement.root.context,
                        "Введіть ім'я",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextEmail.text.isEmpty() ->
                    Toast.makeText(
                        rootElement.root.context,
                        "Введіть email",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPhoneNumber.text.isEmpty() ->
                    Toast.makeText(
                        rootElement.root.context,
                        "Введіть номер телефону",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextRepeatPassword.text.isEmpty() ->
                    Toast.makeText(
                        rootElement.root.context,
                        "Введіть пароль повторно",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPassword.text.isEmpty() ->
                    Toast.makeText(
                        rootElement.root.context,
                        "Введіть пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                editTextPassword.text.toString() != editTextRepeatPassword.text.toString() ->
                    Toast.makeText(
                        rootElement.root.context,
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
                    database.addUser(user)
                    Log.d("SingUp", "User added to db")
                    auth.singUp(user.email.toString(), user.password.toString())
                    Log.d("SingUp", "User sing up")

                    editTextNameOfUser.text = null
                    editTextEmail.text = null
                    editTextPhoneNumber.text = null
                    editTextPassword.text = null
                    editTextRepeatPassword.text = null
                }

            }
        }
    }
}