package com.example.tarealoginalejandrofernandez

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarealoginalejandrofernandez.model.UserModel
import com.example.tarealoginalejandrofernandez.controller.UserManager
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_NEW_REGISTER:Int = 1
    private val REQUEST_CODE_INFORMATION:Int = 2

    private val userManager = UserManager()

    lateinit var buttonRegister: Button
    lateinit var buttonLogin: Button
    lateinit var buttonInfo: Button

    lateinit var loginDialog:LoginDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin = findViewById<Button>(R.id.buttonMainLogin)
        buttonRegister = findViewById<Button>(R.id.buttonMainRegister)
        buttonInfo = findViewById<Button>(R.id.buttonMainInfo)

        // disable the buttons that shouldn't be enabled by default
        buttonLogin.isEnabled = false
        buttonInfo.isEnabled = false


    }

    override fun onStart() {
        super.onStart()

        try {
            userManager.load(this.filesDir)

            showToast("There are " + userManager.count() + " users already registered")

            if (userManager.count() > 0) {
                buttonLogin.isEnabled = true
            }
        } catch (ex : Exception) {
            showToast("No registered users were found")
            ex.printStackTrace()
        }

    }

    fun showToast(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun onRegister(view: View) {
        var intentRegistro = Intent(this, FormActivity::class.java)
        intentRegistro.putExtra("update", false)
        startActivityForResult(intentRegistro, REQUEST_CODE_NEW_REGISTER)
    }
    fun onLogin(view: View) {
        loginDialog = LoginDialog()
        loginDialog.mainActivity = this
        //loginDialog.usuario = usuario
        loginDialog.mainActivity = this
        loginDialog.show(supportFragmentManager, "loginDialog")
    }
    fun onInfo(view: View) {

        var intentRegistro = Intent(this, FormActivity::class.java)
        intentRegistro.putExtra("update", true)

        // set all user data
        var user = userManager.activeUser
        intentRegistro.putExtra("username", user.username)
        intentRegistro.putExtra("password", user.password)
        intentRegistro.putExtra("name", user.name)
        intentRegistro.putExtra("surname", user.surname)

        startActivityForResult(intentRegistro, REQUEST_CODE_INFORMATION)
    }

    fun onDialogLogin(view: View) {
        var user = loginDialog.getUsername()
        var pass = loginDialog.getPassword()
        if (userManager.login(user, pass)) {
            loginDialog.dismiss()
            buttonInfo.isEnabled = true
        } else {
            Toast.makeText(loginDialog.activity, "Wrong credentials", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Confirm that you want to close the app")
        builder.setPositiveButton("Yes") { _, _ -> finish()}
        builder.setNegativeButton("Cancel") { _, _ -> /*nothing to do*/ }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == RESULT_OK) {

            val username = intent!!.getStringExtra("username")
            val password = intent.getStringExtra("password")
            val name = intent.getStringExtra("name")
            val surname = intent.getStringExtra("surname")
            val user = UserModel(username!!, password!!, name!!, surname!!)

            if (requestCode == REQUEST_CODE_NEW_REGISTER) {
                // new register

                userManager.registerUser(user)
                showToast("User registed: " + username)
            }

            if (requestCode == REQUEST_CODE_INFORMATION) {
                // user updated

                userManager.updateUser(user)

            }

            userManager.save()
        }
    }
}