package ru.mirea.lukutin.mireaproject

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//aboba@mail.ru aboba123

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var emailPlainText: EditText
    lateinit var passwordPlainText: EditText
    lateinit var logRegButton : Button
    lateinit var statusTextView : TextView
    var currentUser: FirebaseUser? = null
    var authHelper : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        emailPlainText = findViewById(R.id.emailPlainText)
        passwordPlainText = findViewById(R.id.passwordPlainText)
        statusTextView = findViewById(R.id.statusTextView)
        logRegButton = findViewById(R.id.loginRegisterButton)
        logRegButton.setOnClickListener {
            view -> onRegLogClick(view)
        }
    }

    public override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser
        statusTextView.text = currentUser?.email.toString()
    }

    fun createAccount(){
        auth.createUserWithEmailAndPassword(emailPlainText.text.toString(), passwordPlainText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    authHelper = false
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    authHelper = true
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val mainIntent = Intent(this,MainActivity::class.java)
        val helloToast = Toast.makeText(this, "Hello " + user?.email.toString(), Toast.LENGTH_LONG)
        helloToast.show()
        startActivity(mainIntent)
    }

    fun signIn(){
        auth.signInWithEmailAndPassword(emailPlainText.text.toString(), passwordPlainText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun onRegLogClick(view: View){
        if(currentUser != null){
            updateUI(currentUser)
        }
        else if((isValidEmail(emailPlainText.text.toString())
                    && isValidPassword(passwordPlainText.text.toString()))
                    && !authHelper){
            signIn()
        }
        else{
            createAccount()
        }
    }

    fun isValidEmail(mail: String) : Boolean{
        val regex = Regex("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")
        if(regex.matches(mail))
            return true
        return false
    }

    fun isValidPassword(pass: String): Boolean{
        if(pass.length > 5 && pass.isNotBlank())
            return true
        return false
    }

}