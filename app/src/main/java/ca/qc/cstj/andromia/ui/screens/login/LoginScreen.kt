/*
Sources UI: https://medium.com/@manojbhadane/android-login-screen-using-jetpack-compose-part-1-57ecfc74e428
*/

package ca.qc.cstj.andromia.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.Screen
import ca.qc.cstj.andromia.ui.composables.LoadingSpinner

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val loginUIState by loginViewModel.loginUiState.collectAsState()
    val context = LocalContext.current

    when (val state = loginUIState) {
        is LoginUIState.Error -> {
            DisplayScreen(loginViewModel, navController)
            Toast.makeText(context, state.exception.message, Toast.LENGTH_LONG).show()
        }

        LoginUIState.Loading -> LoadingSpinner()
        is LoginUIState.Success -> {
            navController.navigate(Screen.User.route)
        }

        LoginUIState.Empty -> DisplayScreen(loginViewModel, navController)
    }
}


@Composable
private fun DisplayScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "Login background",
        contentScale = ContentScale.Crop
    )
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString(stringResource(R.string.sign_up_here)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 20.dp),
            onClick = {
                navController.navigate("register")
            },
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val showPassword = remember { mutableStateOf(false) }

        Text(
            text = stringResource(R.string.andromia),
            style = TextStyle(
                fontSize = 65.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(R.string.username)) },
            value = username.value,
            onValueChange = { username.value = it }, leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = stringResource(R.string.account_icon)
                )
            })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = password.value,
            onValueChange = { newText ->
                password.value = newText
            },
            label = { Text(text = stringResource(R.string.password)) },
            placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = stringResource(R.string.lock_icon)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        painter = if (showPassword.value) painterResource(id = R.drawable.outline_visibility_off_24) else painterResource(
                            id = R.drawable.outline_visibility_24
                        ),
                        contentDescription = if (showPassword.value) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )
                    )
                }
            },
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (
                        username.value.text.isNotEmpty() && password.value.text.isNotEmpty()
                    ) {
                        loginViewModel.login(username.value.text, password.value.text)
                    } else {
                        Toast.makeText(
                            context,
                            "Fields must be filled and valid.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.login), style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
