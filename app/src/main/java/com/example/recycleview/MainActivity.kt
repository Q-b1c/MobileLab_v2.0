package com.example.recycleview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }


    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    //    val dbHelper = DBHelper(this)
    private lateinit var adapter: RecyclerAdapter
    var ListOfContacts = mutableListOf<ContactEntity>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactDao = contactDatabase
        ListOfContacts.addAll(contactDao.all)
        setContentView(R.layout.activity_main)
        adapter = RecyclerAdapter(ListOfContacts) {
            val intent = Intent(this, ContactActivity::class.java)
            intent.putExtra(EXTRA_KEY, ListOfContacts[it].id)
            startActivity(intent)
//            contactDao.delete(ListOfContacts[it])
//            ListOfContacts.removeAt(it)
//            adapter.notifyItemRemoved(it)
        }
        val RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        val Contact = findViewById<EditText>(R.id.EditText)
        val button = findViewById<Button>(R.id.button)
        RecyclerView.adapter = adapter
        button.setOnClickListener() {
            //val id = dbHelper.add(Contact.text.toString())
            val editContactActivity = Intent(this, EditContactActivity::class.java)
            startActivity(editContactActivity)
        }
    }
}