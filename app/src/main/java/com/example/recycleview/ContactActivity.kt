package com.example.recycleview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ContactActivity : AppCompatActivity() {
    //@SuppressLint("MissingInflatedId")
    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val id = intent.getLongExtra(MainActivity.EXTRA_KEY,-1)
        val firstNameText = findViewById<TextView>(R.id.textFirstName)
        val lastNameText = findViewById<TextView>(R.id.textLastName)
        val birthDayDate = findViewById<TextView>(R.id.textBirthdayDate)
        val phoneNumberText = findViewById<TextView>(R.id.textPhoneNumber)
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val userEntity = contactDatabase.getById(id)
        firstNameText.text = userEntity.firstName
        lastNameText.text = userEntity.lastName
        birthDayDate.text = userEntity.birthdayDate
        phoneNumberText.text = userEntity.phoneNumber
        buttonBack.setOnClickListener {
            finish()
        }
        buttonEdit.setOnClickListener{
            val startContactActivity = Intent(this, EditContactActivity::class.java)
            startActivity(startContactActivity)
        }
    }
}