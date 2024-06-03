package com.example.xml_catalog_parser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CD(
    val title: String?,
    val artist: String?,
    val country: String?,
    val company: String?,
    val price: String?,
    val year: String?
    ) : Parcelable
