package ca.qc.cstj.andromia.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


//https://developer.android.com/jetpack/compose/components/dialog
@Composable
fun ErrorDialog(title: String, message: String, onDismissClick: () -> Unit) {
    AlertDialog(
        title = { Text(text = title) },
        text =  { Text(text= message) },
        onDismissRequest = { /*TODO*/ },
        confirmButton = { /*TODO*/ })

}