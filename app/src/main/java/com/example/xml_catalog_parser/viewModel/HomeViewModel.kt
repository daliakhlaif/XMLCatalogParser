package com.example.xml_catalog_parser.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xml_catalog_parser.model.CD
import com.example.xml_catalog_parser.service.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class HomeViewModel : ViewModel() {

    private val _catalog = MutableLiveData<List<CD>>()
    val catalog: LiveData<List<CD>> get() = _catalog

    fun loadCatalog() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://www.w3schools.com/xml/cd_catalog.xml")
                val inputStream = url.openStream()
                val cds = XmlParser().parse(inputStream)
                withContext(Dispatchers.Main) {
                    _catalog.value = cds
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
