package com.example.xml_catalog_parser.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xml_catalog_parser.model.CD
import com.example.xml_catalog_parser.util.GlobalKeys
import com.example.xml_catalog_parser.util.getParcelableExtraCompat

class DetailsViewModel : ViewModel() {

    private val _cd = MutableLiveData<CD>()
    val cd: LiveData<CD>
        get() = _cd

    fun init(intent: Intent) {
        val cd = intent.getParcelableExtraCompat<CD>(GlobalKeys.CD, CD::class.java)
        _cd.value = cd
    }
}
