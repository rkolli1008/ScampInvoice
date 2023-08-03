package com.assignment.scampinvoicetest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invoice(
    val id: Int,
    val invoiceNumber: String,
    val customerName: String,
    val amount: Double,
    val date: String,
    val dueDate: String,
    val status: String
): Parcelable
