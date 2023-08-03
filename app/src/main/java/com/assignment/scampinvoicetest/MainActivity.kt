package com.assignment.scampinvoicetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignment.scampinvoicetest.ui.theme.ScampInvoiceTestTheme
import com.assignment.scampinvoicetest.ui.views.EditInvoiceScreen
import com.assignment.scampinvoicetest.ui.views.InvoiceListScreen
import com.assignment.scampinvoicetest.viewmodel.InvoiceViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by lazy {
        InvoiceViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScampInvoiceTestTheme {
                App(viewModel)
            }
        }
    }
}

@Composable
fun App(viewModel: InvoiceViewModel) {
    val navController = rememberNavController()

    val invoices by viewModel.invoices.observeAsState(emptyList())

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            InvoiceListScreen(
                invoices = invoices, // Use the StateFlow value directly
                onEditInvoice = { invoice ->
                    navController.navigate("edit/${invoice.id}")
                }
            )
        }
        composable(
            "edit/{invoiceId}",
            arguments = listOf(navArgument("invoiceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val invoiceId = backStackEntry.arguments?.getInt("invoiceId") ?: 0
            val currentInvoice = invoices.find { it.id == invoiceId }
            currentInvoice?.let { invoice ->
                EditInvoiceScreen(
                    invoice = invoice,
                    onSaveInvoice = { updatedInvoice ->
                        viewModel.saveInvoices(
                            invoices.map {
                                if (it.id == updatedInvoice.id) updatedInvoice else it
                            }
                        )
                        navController.popBackStack()
                    },
                    onCancelEditing = {
                        // Navigate back when cancel is clicked
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

