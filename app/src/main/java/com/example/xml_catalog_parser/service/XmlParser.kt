package com.example.xml_catalog_parser.service

import com.example.xml_catalog_parser.model.CD
import com.example.xml_catalog_parser.util.GlobalKeys
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class XmlParser {

    private val ns: String? = null

    @Throws(XmlPullParserException::class, java.io.IOException::class)
    fun parse(inputStream: InputStream): List<CD> {
        inputStream.use { inputStream ->
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, java.io.IOException::class)
    private fun readFeed(parser: XmlPullParser): List<CD> {
        val cds = mutableListOf<CD>()

        parser.require(XmlPullParser.START_TAG, ns, "CATALOG")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == GlobalKeys.CD) {
                cds.add(readCD(parser))
            } else {
                skip(parser)
            }
        }
        return cds
    }

    @Throws(XmlPullParserException::class, java.io.IOException::class)
    private fun readCD(parser: XmlPullParser): CD {
        parser.require(XmlPullParser.START_TAG, ns, GlobalKeys.CD)
        var title: String? = null
        var artist: String? = null
        var country: String? = null
        var company: String? = null
        var price: String? = null
        var year: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "TITLE" -> title = readText(parser)
                "ARTIST" -> artist = readText(parser)
                "COUNTRY" -> country = readText(parser)
                "COMPANY" -> company = readText(parser)
                "PRICE" -> price = readText(parser)
                "YEAR" -> year = readText(parser)
                else -> skip(parser)
            }
        }
        return CD(title, artist, country, company, price, year)
    }

    @Throws(XmlPullParserException::class, java.io.IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    @Throws(XmlPullParserException::class, java.io.IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }
}