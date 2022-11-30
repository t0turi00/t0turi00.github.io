package com.example.mahtis

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.app.ProgressDialog
import android.widget.EditText
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.text.TextWatcher
import android.text.Editable
import android.content.Intent
import android.view.View
import java.util.*

class MainActivity : AppCompatActivity() {
    var mRecyclerView: RecyclerView? = null
    var myFoodList: MutableList<FoodData?>? = null
    var mFoodData: FoodData? = null
    var progressDialog: ProgressDialog? = null
    var myAdapter: MyAdapter? = null
    var txt_Search: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val gridLayoutManager = GridLayoutManager(this@MainActivity, 1)
        mRecyclerView!!.layoutManager = gridLayoutManager
        txt_Search = findViewById<View>(R.id.txt_searchtext) as EditText
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Ladataan Reseptejä....")
        myFoodList = ArrayList()
        myAdapter = MyAdapter(this@MainActivity, myFoodList)
        mRecyclerView!!.adapter = myAdapter
        val databaseReference = FirebaseDatabase.getInstance().getReference("Recipe")
        progressDialog!!.show()
        val eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (myFoodList as ArrayList<FoodData?>).clear()
                for (itemSnapshot in dataSnapshot.children) {
                    val foodData = itemSnapshot.getValue(FoodData::class.java)
                    foodData!!.key = itemSnapshot.key
                    (myFoodList as ArrayList<FoodData?>).add(foodData)
                }
                myAdapter!!.notifyDataSetChanged()
                progressDialog!!.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog!!.dismiss()
            }
        })
        txt_Search!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun filter(text: String) {
        val filterList = ArrayList<FoodData?>()
        for (item in myFoodList!!) {
            if (item!!.itemName!!.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filterList.add(item)
            }
        }
        myAdapter!!.filteredList(filterList)
    }

    fun btn_uploadActivity(view: View?) {
        startActivity(Intent(this, Upload_Recipe::class.java))
    }
}