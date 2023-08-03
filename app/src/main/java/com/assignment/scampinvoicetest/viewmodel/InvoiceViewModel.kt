package com.assignment.scampinvoicetest.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.scampinvoicetest.repository.InvoiceRepository
import com.assignment.scampinvoicetest.models.Invoice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InvoiceViewModel(context: Context) : ViewModel() {
    private val invoiceRepository = InvoiceRepository(context)

    private val _invoices = MutableLiveData<List<Invoice>>(emptyList())
    val invoices: LiveData<List<Invoice>> = _invoices

    init {
        loadInvoices()
    }

    private fun loadInvoices() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val loadedInvoices = invoiceRepository.getInvoices()
                _invoices.postValue(loadedInvoices)
            }
        }
    }

    fun saveInvoices(updatedInvoices: List<Invoice>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                invoiceRepository.saveInvoices(updatedInvoices)
                _invoices.postValue(updatedInvoices) // Update LiveData with new data
            }
        }
    }
}
