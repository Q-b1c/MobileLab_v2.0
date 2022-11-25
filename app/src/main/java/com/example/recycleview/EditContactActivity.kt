package com.example.recycleview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EditContactActivity : AppCompatActivity() {
    //    @SuppressLint("MissingInflatedId")
    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
//        val dbHelper = DBHelper(this)
        val textEditName = findViewById<EditText>(R.id.editTextName)
        val textEditLastName = findViewById<EditText>(R.id.editTextLastName)
        val textEditBirthday = findViewById<EditText>(R.id.editTextBirthday)
        val textEditPNumber = findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonDone = findViewById<Button>(R.id.buttonDone)
        var contactEntity: ContactEntity = ContactEntity()
        buttonDone.setOnClickListener {
            contactEntity.firstName = textEditName.text.toString()
            contactEntity.lastName = textEditLastName.text.toString()
            contactEntity.birthdayDate = textEditBirthday.text.toString()
            contactEntity.phoneNumber = textEditPNumber.text.toString()
            contactDatabase.insert(contactEntity)
            finish()
        }


    }
}