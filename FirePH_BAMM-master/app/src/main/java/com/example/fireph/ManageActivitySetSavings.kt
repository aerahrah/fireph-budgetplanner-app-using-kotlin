package com.example.fireph

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ManageActivitySetSavings : AppCompatActivity() {
    private var item: String = ""
    private lateinit var etSavings: TextInputLayout
    private lateinit var btnSaveData: Button
    private lateinit var autoCompleteTxt: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String?>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setsaving)

        etSavings = findViewById(R.id.etSaving)
        btnSaveData = findViewById(R.id.btnSave)

        val items = arrayOf(
            "10","15","20","25","30","35","40","45",
            "50","55","60","65","70","75"
        )
        saveType(items)
        btnSaveData.setOnClickListener {
            saveData()
        }
    }

    private fun saveType(array: Array<String>) {
        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.list_menu, array)
        autoCompleteTxt.setText("")
        autoCompleteTxt.setAdapter(adapterItems)
        item=""

        autoCompleteTxt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideSoftKeyboard() // Using activity extension function
            }
        }

        autoCompleteTxt.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val category = parent.getItemAtPosition(position).toString()
                saveItem(category)
            }
    }

    private fun saveItem(category: String) {
        item = category
    }

    fun Activity.hideSoftKeyboard() {
        val inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = this.currentFocus
        val windowToken = this.window?.decorView?.rootView?.windowToken
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        inputMethodManager.hideSoftInputFromWindow(
            windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    private fun saveData() {
        val fireCategory = item
        dbRef = FirebaseDatabase.getInstance("https://budgetfireph-default-rtdb.firebaseio.com/")
            .getReference("Users")

        if (fireCategory.isEmpty()) {
            Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
            return
        }

        val moneyId = dbRef.push().key!!

    }
}
