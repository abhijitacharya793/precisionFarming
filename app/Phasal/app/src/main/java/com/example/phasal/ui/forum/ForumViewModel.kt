package com.example.phasal.ui.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForumViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Phasal"
    }
    val title: LiveData<String> = _text
}