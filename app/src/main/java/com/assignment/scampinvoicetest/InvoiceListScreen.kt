package com.assignment.scampinvoicetest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.scampinvoicetest.data.Invoice

@Composable
fun InvoiceListScreen(
    invoices: List<Invoice>,
    onEditInvoice: (Invoice) -> Unit
) {
    if (invoices.isEmpty()) return

    // Manually trigger recomposition using LaunchedEffect
    LaunchedEffect(invoices) {
        println("InvoiceListScreen: Invoices List = $invoices")
    }

    LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState()) {
        items(items = invoices) { invoice ->
            InvoiceListItem(
                invoice = invoice,
                onEditInvoice = { onEditInvoice(invoice) })
        }
    }
}


@Composable
fun InvoiceListItem(invoice: Invoice, onEditInvoice: (Invoice) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onEditInvoice(invoice) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Invoice Number: ${invoice.invoiceNumber}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Customer Name: ${invoice.customerName}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Amount: ${invoice.amount}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${invoice.date}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Due Date: ${invoice.dueDate}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: ${invoice.status}")
        }
    }
}


