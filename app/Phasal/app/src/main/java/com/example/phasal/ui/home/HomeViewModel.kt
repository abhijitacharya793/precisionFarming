package com.example.phasal.ui.home

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androdocs.httprequest.HttpRequest
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel : ViewModel() {

    // Title
    private val _title = MutableLiveData<String>().apply {
        value = "Phasal"
    }
    val title: LiveData<String> = _title

    // Temperature
    private val _temperature = MutableLiveData<String>().apply {
        value = "NA"
    }
    val temperature: LiveData<String> = _temperature

    // Place
    private val _place = MutableLiveData<String>().apply {
        value = "Airoli"
    }
    val place: LiveData<String> = _place

    // Date
    var dateValue = SimpleDateFormat("MMMM dd", Locale.getDefault()).format(Date()).toString()
    private val _date = MutableLiveData<String>().apply {
        value = dateValue
    }
    val date: LiveData<String> = _date


}