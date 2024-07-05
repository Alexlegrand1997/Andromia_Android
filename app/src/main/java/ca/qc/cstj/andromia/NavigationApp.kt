package ca.qc.cstj.andromia

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ca.qc.cstj.andromia.ui.composables.BottomNavigationBar
import ca.qc.cstj.andromia.ui.screens.Ally.AllyScreen
import ca.qc.cstj.andromia.ui.screens.register.RegisterScreen
import ca.qc.cstj.andromia.ui.screens.login.LoginScreen
import ca.qc.cstj.andromia.ui.screens.booster.BoosterScreen
import ca.qc.cstj.andromia.ui.screens.portalInfo.PortalInfoScreen
import ca.qc.cstj.andromia.ui.screens.portals.PortalScreen
import ca.qc.cstj.andromia.ui.screens.user.UserScreen
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode

//https://nameisjayant.medium.com/nested-navigation-in-jetpack-compose-597ecdc6eebb

@Composable
fun NavigationApp(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route, content = { LoginScreen(navController) })
        composable(Screen.Register.route, content = { RegisterScreen(navController) })
        navigation(route = Screen.Home.route, startDestination = Screen.User.route) {
            composable(Screen.User.route) {
                BottomNavScreen(navController) {
                    UserScreen(navController)
                }
            }
            composable(Screen.Booster.route)
            {
                BottomNavScreen(navController) {
                    BoosterScreen(navController)
                }
            }
            composable(Screen.Ally.route)
            {
                BottomNavScreen(navController) {
                    AllyScreen(navController)
                }
            }
            composable(Screen.Portal.route)
            {
                BottomNavScreen(navController) {
                    PortalScreen(navController)
//                    LaunchedEffect(key1 = "camera", block = {scanQrCodeLauncher.launch(null)})
                }
            }
            composable(route= Screen.PortalInfo.route,
                arguments = listOf(
                    navArgument("qrCode"){type= NavType.StringType}
                ))
            {
                val qrCode = it.arguments?.getString("qrCode")!!
                BottomNavScreen(navController) {
                    PortalInfoScreen(navController, qrCode)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavScreen(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

//sealed class Screens(@StringRes val title: Int) {
sealed class Screen(val title: String, val route: String) {

    object Login : Screen("Login", route = "login")
    object Home : Screen("Home", route = "home")
    object User : Screen("User", route = "user")
    object Booster : Screen("Boosters", route = "boosters")
    object Ally : Screen("Allies", route = "allies")
    object Portal : Screen("Portals", route = "portals")
    object PortalInfo : Screen("PortalInfo", route = "portalInfo/{qrCode}")
    object Register : Screen("Register", route = "register")
}