package com.example.tarealoginalejandrofernandez

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class FormActivity : AppCompatActivity() {

    lateinit var editTextUser: EditText
    lateinit var editTextPass: EditText
    lateinit var editTextName: EditText
    lateinit var editTextSur: EditText
    lateinit var buttonAccept: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextUser = findViewById<EditText>(R.id.editTextUsername)
        editTextPass = findViewById<EditText>(R.id.editTextPassword)
        editTextName = findViewById<EditText>(R.id.editTextName)
        editTextSur = findViewById<EditText>(R.id.editTextSurname)

        buttonAccept = findViewById<Button>(R.id.buttonFormAccept)

        val isUpdate = intent.getBooleanExtra("update", true)
        if (isUpdate) {
            buttonAccept.text = "Update"
            editTextUser.setText(intent.getStringExtra("username").toString())
            editTextUser.isEnabled = false;
            editTextPass.setText(intent.getStringExtra("password").toString())
            editTextName.setText(intent.getStringExtra("name").toString())
            editTextSur.setText(intent.getStringExtra("surname").toString())
        }

    }

    fun onAccept(view: View) {
        val returnIntent = Intent()

        returnIntent.putExtra("result", "create")
        returnIntent.putExtra("username", editTextUser.text.toString())
        returnIntent.putExtra("password", editTextPass.text.toString())
        returnIntent.putExtra("name", editTextName.text.toString())
        returnIntent.putExtra("surname", editTextSur.text.toString())

        setResult(RESULT_OK, returnIntent)
        finish()
    }

    fun onCancel(view: View) {
        val returnIntent = Intent()
        returnIntent.putExtra("result", "canceled")
        setResult(RESULT_CANCELED, returnIntent)
        finish()
    }

}