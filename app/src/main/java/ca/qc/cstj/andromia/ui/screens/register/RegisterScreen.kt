package ca.qc.cstj.andromia.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.Screen
import ca.qc.cstj.andromia.ui.composables.LoadingSpinner

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val registerUIState by registerViewModel.registerUIState.collectAsState()
    val context = LocalContext.current

    when (val state = registerUIState) {
        is RegisterUIState.Error -> {
            registerForm(registerViewModel, navController = navController)
            Toast.makeText(
                context,
                state.exception.message,
                Toast.LENGTH_LONG
            ).show()
        }

        RegisterUIState.Loading -> LoadingSpinner()
        RegisterUIState.Empty -> registerForm(registerViewModel, navController = navController)
        is RegisterUIState.Success -> navController.navigate(Screen.User.route)
    }

}

@Composable
fun registerForm(
    registerViewModel: RegisterViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = stringResource(id = R.string.mainbackground),
        contentScale = ContentScale.Crop
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val showPassword = remember { mutableStateOf(false) }
        val isErrorEmail = remember { mutableStateOf(false) }
        val isErrorPassword = remember { mutableStateOf(false) }
        Text(
            text = stringResource(id = R.string.andromia),
            style = TextStyle(
                fontSize = 65.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            text = stringResource(R.string.registration),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(id = R.string.username)) },
            value = username.value,
            onValueChange = { username.value = it },
            isError = username.value.text.isEmpty(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = stringResource(id = R.string.account_icon)
                )
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(R.string.email)) },
            value = email.value,
            onValueChange = { email.value = it; isErrorEmail.value = !isValidEmail(email) },
            isError = isErrorEmail.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(R.string.email_icon)
                )
            },
            supportingText = {
                if (isErrorEmail.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.exemple_email_com),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = password.value,
            isError = isErrorPassword.value,
            onValueChange = { newText ->
                password.value = newText
                isErrorPassword.value = !isValidPassword(password)
            },
            label = { Text(text = stringResource(id = R.string.password)) },
            placeholder = { Text(text = stringResource(id = R.string.enter_your_password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = stringResource(id = R.string.lock_icon)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        painter = if (showPassword.value) painterResource(id = R.drawable.outline_visibility_off_24) else painterResource(
                            id = R.drawable.outline_visibility_24
                        ),
                        contentDescription = if (showPassword.value) stringResource(id = R.string.show_password) else stringResource(
                            id = R.string.hide_password
                        )
                    )
                }
            },
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = {
                if (isErrorPassword.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.password_format),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (
                        username.value.text.isNotEmpty() && isValidEmail(email) && isValidPassword(
                            password
                        )
                    ) {
                        registerViewModel.register(
                            username.value.text,
                            email.value.text,
                            password.value.text
                        )
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
                    text = stringResource(id = R.string.confirm), style = TextStyle(
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

fun isValidEmail(email: MutableState<TextFieldValue>): Boolean {
    return email.value.text.trim().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}

// Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
fun isValidPassword(password: MutableState<TextFieldValue>): Boolean {
    return password.value.text.trim()
        .matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
}
