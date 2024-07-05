package ca.qc.cstj.andromia.ui.screens.portals

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import ca.qc.cstj.andromia.Screen
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode


@Composable
fun PortalScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scanQrCodeLauncher = rememberLauncherForActivityResult(ScanQRCode()) { result ->
        when (result) {
            is QRResult.QRError -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            QRResult.QRMissingPermission -> Toast.makeText(
                context,
                "Permission invalid",
                Toast.LENGTH_SHORT
            ).show()

            is QRResult.QRSuccess -> {
                var href: String = result.content.rawValue.toString()
                navController.navigate(Screen.PortalInfo.route.replace("{qrCode}", "${href}"))
            }

            QRResult.QRUserCanceled -> Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT)
                .show()
        }
    }
    LaunchedEffect(key1 = "camera", block = { scanQrCodeLauncher.launch(null) })
}

