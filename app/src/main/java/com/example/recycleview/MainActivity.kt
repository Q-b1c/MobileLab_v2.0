package com.example.recycleview

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
    val dbHelper = DBHelper(this)
    private lateinit var adapter: RecyclerAdapter
    var ListOfContacts = mutableListOf<Contact>() // список, который потом привязывается к RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListOfContacts.addAll(dbHelper.GetAll())
        setContentView(R.layout.activity_main)
         adapter = RecyclerAdapter(ListOfContacts){
            // адаптеру передали обработчик удаления элемента
             dbHelper.remove(ListOfContacts[it].id)
             ListOfContacts.removeAt(it)
             adapter.notifyItemRemoved(it)
        }
        val RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        val Contact = findViewById<EditText>(R.id.EditText)
        val button = findViewById<Button>(R.id.button)
        RecyclerView.adapter = adapter
        button.setOnClickListener(){
            val id = dbHelper.add(Contact.text.toString())
            ListOfContacts.clear()
            ListOfContacts.addAll(dbHelper.GetAll())
            RecyclerView.adapter = adapter
        }
    }
}