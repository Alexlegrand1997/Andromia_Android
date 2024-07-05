package ca.qc.cstj.andromia.ui.screens.portalInfo

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.Screen
import ca.qc.cstj.andromia.models.InfoExploration
import ca.qc.cstj.andromia.ui.composables.AffinityPicture
import ca.qc.cstj.andromia.ui.composables.InventoryList
import ca.qc.cstj.andromia.ui.composables.LoadingSpinner
import ca.qc.cstj.andromia.ui.screens.Ally.AllyCardItem


@Composable
fun PortalInfoScreen(
    navController: NavHostController,
    qrCode: String,
    portalInfoViewModel: PortalInfoViewModel = viewModel()
) {
    val portalInfoUIState by portalInfoViewModel.portalUIState.collectAsState()
    val context = LocalContext.current

    when (val state = portalInfoUIState) {
        is PortalInfoUIState.Error -> Toast.makeText(
            context,
            state.exception.message,
            Toast.LENGTH_LONG
        ).show()

        PortalInfoUIState.Loading -> LoadingSpinner()
        is PortalInfoUIState.Success -> {
            explorationInfo(
                state.exploration.exploration,
                navController,
                portalInfoViewModel,
                context
            )
        }
    }
}

@Composable
fun explorationInfo(
    exploration: InfoExploration,
    navController: NavHostController,
    viewModel: PortalInfoViewModel,
    context: Context
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 10.dp, 15.dp, 0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.secondary)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Text(
                        text = "Destination: ${exploration.destination}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(modifier = Modifier.align(alignment = Alignment.Start)) {
                        Text(
                            text = "Found inox: ${exploration.vault.inox}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.inox),
                            contentDescription = stringResource(id = R.string.inox),
                            Modifier
                                .size(25.dp)
                                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                    Row(modifier = Modifier.align(alignment = Alignment.Start)) {
                        Text(
                            text = stringResource(R.string.affinity),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        AffinityPicture(
                            exploration.affinity,
                            modifier = Modifier
                                .fillMaxHeight(0.05f)
                                .fillMaxHeight(0.05f)
                        )
                    }
                }

            }

            if (exploration.vault.elements.isNotEmpty()) {
                Row(modifier = Modifier.align(alignment = CenterHorizontally)) {
                    Text(
                        text = stringResource(R.string.elements), fontWeight = FontWeight.Bold, fontSize = 10.em
                    )
                }
                InventoryList(
                    elements = exploration.vault.elements,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(15.dp, 10.dp, 15.dp, 0.dp),
                )
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp, 15.dp, 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.padding(25.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.no_found_elements),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            if (exploration.ally.uuid.isNotEmpty()) {

                val modifier1 = if (exploration.vault.elements.isNotEmpty()) {
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp, 15.dp, 10.dp)
                } else {
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(15.dp, 10.dp, 15.dp, 10.dp)
                }
                Row(
                    modifier = modifier1
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                        AllyCardItem(exploration.ally)
                    }

                    val portalInfoAllyUIState by viewModel.portalInfoAllyUIState.collectAsState()
                    val interactionSource = remember { MutableInteractionSource() }
                    Column {

                        Button(
                            onClick = {
                                captureAlly(
                                    exploration.ally.captureMe,
                                    viewModel
                                )
                            }, interactionSource = interactionSource,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(8.dp)

                        ) {
                            Text(text = stringResource(R.string.capture), textAlign = TextAlign.Center)
                        }
                        LaunchedEffect(portalInfoAllyUIState) {
                            when (val state = portalInfoAllyUIState) {
                                is PortalInfoAllyUIState.Error -> Toast.makeText(
                                    context,
                                    state.exception.message,
                                    Toast.LENGTH_LONG
                                ).show()

                                PortalInfoAllyUIState.Loading -> PortalInfoAllyUIState.Loading
                                is PortalInfoAllyUIState.Success -> navController.navigate(Screen.User.route)
                            }
                        }
                        Button(
                            onClick = { navController.navigate(Screen.User.route) },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(8.dp)
                        ) {
                            Text(text = stringResource(R.string.release), textAlign = TextAlign.Center)
                        }
                    }
                }

            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp, 15.dp, 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.padding(25.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.no_found_ally),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

fun captureAlly(
    captureMeHref: String,
    viewModel: PortalInfoViewModel,
) {
    viewModel.capture(captureMeHref)

}



