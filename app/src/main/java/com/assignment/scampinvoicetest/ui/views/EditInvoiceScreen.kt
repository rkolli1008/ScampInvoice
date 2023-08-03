package com.assignment.scampinvoicetest.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.scampinvoicetest.models.Invoice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditInvoiceScreen(
    invoice: Invoice,
    onSaveInvoice: (Invoice) -> Unit,
    onCancelEditing: () -> Unit
) {
    var editedInvoice by remember { mutableStateOf(invoice) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = editedInvoice.invoiceNumber,
            onValueChange = { editedInvoice = editedInvoice.copy(invoiceNumber = it) },
            label = { Text("Invoice Number") }
        )

        TextField(
            value = editedInvoice.customerName,
            onValueChange = { editedInvoice = editedInvoice.copy(customerName = it) },
            label = { Text("Customer Name") }
        )

        // Add more fields for amount, date, due date, status, etc.

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { onSaveInvoice(editedInvoice) }) {
                Text("Save Invoice")
            }

            Button(onClick = {
                onCancelEditing()
            }) {
                Text("Cancel")
            }
        }
    }
}
