package com.example.masimelrowoo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masimelrowoo.entity.User

class HomeViewModel : ViewModel() {


    private val _text = MutableLiveData<User>()

    fun setSelectedNews(person: User) {
        _text.value = person
    }
    fun getSelectedNews() = _text.value

    val text: LiveData<User> = _text
}