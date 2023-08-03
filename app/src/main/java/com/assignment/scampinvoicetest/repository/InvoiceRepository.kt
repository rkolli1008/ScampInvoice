package com.assignment.scampinvoicetest.repository

import android.content.Context
import android.util.Log
import com.assignment.scampinvoicetest.models.Invoice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class InvoiceRepository(private val context: Context) {
    private val filename = "invoices.json"
    private val gson = Gson()

    fun getInvoices(): List<Invoice> {
        // Check if the file exists in internal storage
        val internalFile = File(context.filesDir, filename)
        return if (internalFile.exists()) {
            // Read from internal storage
            val jsonString = readFromInternalStorage()
            gson.fromJson(jsonString, object : TypeToken<List<Invoice>>() {}.type)
        } else {
            // Read from assets
            val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
            gson.fromJson(jsonString, object : TypeToken<List<Invoice>>() {}.type)
        }
    }

    private fun readFromInternalStorage(): String {
        val fileInputStream = context.openFileInput(filename)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        return stringBuilder.toString()
    }

    fun saveInvoices(updatedInvoices: List<Invoice>) {
        try {
            val jsonString = gson.toJson(updatedInvoices)
            context.openFileOutput(filename, Context.MODE_PRIVATE).use { it.write(jsonString.toByteArray()) }
            val invoices = getInvoices().toString()
            println(invoices)
        } catch (e: Exception) {
            // Handle the exception (e.g., log the error or show an error message)
            Log.e("InvoiceRepository", "Error saving invoices: ${e.message}")
        }
    }
}
