package com.example.recycleview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    private lateinit var adapter: RecyclerAdapter
    var ListOfContacts = mutableListOf<ContactEntity>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactDao = contactDatabase
        setContentView(R.layout.activity_main)
        setTitle("Contacts")
        adapter = RecyclerAdapter(ListOfContacts) {
            val intent = Intent(this, ContactActivity::class.java)
            intent.putExtra(EXTRA_KEY, ListOfContacts[it].id)
            startActivity(intent)
            finish()
        }
        val RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        ListOfContacts.clear()
        ListOfContacts.addAll(contactDao.all)
        val ContactFind = findViewById<EditText>(R.id.EditText)
        val buttonFind = findViewById<Button>(R.id.findButton)
        val buttonCreateContact = findViewById<Button>(R.id.button)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.adapter = adapter
        buttonCreateContact.setOnClickListener() {
            val editContactActivity = Intent(this, EditContactActivity::class.java)
            startActivity(editContactActivity)
            finish()
        }
        buttonFind.setOnClickListener() {
            if (ContactFind.text.isEmpty()) {
                ListOfContacts.clear()
                ListOfContacts.addAll(contactDao.all)
                adapter.updateAdapter(ListOfContacts)
                adapter.run { notifyDataSetChanged() }
            } else {
                adapter.updateAdapter(ListOfContacts.filter {
                    ContactFind.text.toString().lowercase() in it.firstName.lowercase() ||
                            ContactFind.text.toString().lowercase() in it.lastName.lowercase()
                })
                adapter.run { notifyDataSetChanged() }
            }
        }
    }
}