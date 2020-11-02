package com.example.tarealoginalejandrofernandez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class LoginDialog : DialogFragment() {

    lateinit var mainActivity:MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var viewDialog = inflater.inflate(R.layout.login_dialog, container, false)
        var botonLogin = viewDialog.findViewById<Button>(R.id.buttonDialogLogin)
        botonLogin.setOnClickListener{view->login(view)}
        return viewDialog
    }

    fun login(view: View) {
        mainActivity.onDialogLogin(view)
    }

    fun getUsername() : String {
        return dialog!!.findViewById<EditText>(R.id.editTextLoginUsername).text.toString()
    }

    fun getPassword() : String {
        return dialog!!.findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
    }
}