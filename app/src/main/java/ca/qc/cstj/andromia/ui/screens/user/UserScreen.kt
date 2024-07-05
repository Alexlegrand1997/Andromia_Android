package ca.qc.cstj.andromia.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.Screen
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.InfoExploration
import ca.qc.cstj.andromia.ui.composables.AffinityPicture
import ca.qc.cstj.andromia.ui.composables.InventoryList
import ca.qc.cstj.andromia.ui.composables.LoadingSpinner
import com.example.tenretni.core.DateHelper.formatISODate

@Composable
fun UserScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val userUIState by userViewModel.userUIState.collectAsState()
    val explorationsUIState by userViewModel.explorationsUIState.collectAsState()
    var explorations: List<InfoExploration> = listOf()
    when (val state = explorationsUIState) {
        is ExplorationsUIState.Error -> Toast.makeText(
            LocalContext.current,
            state.exception.message,
            Toast.LENGTH_LONG
        ).show()

        ExplorationsUIState.Loading -> LoadingSpinner()
        is ExplorationsUIState.Success -> explorations = state.explorations
    }

    when (val state = userUIState) {
        is UserUIState.Error -> Toast.makeText(
            LocalContext.current,
            state.exception.message,
            Toast.LENGTH_LONG
        ).show()

        UserUIState.Loading -> LoadingSpinner()
        is UserUIState.Success -> ExplorerCard(
            state.account,
            explorations,
            navController,
            userViewModel
        )
    }

}

@Composable
private fun ExplorerCard(
    account: Account,
    explorations: List<InfoExploration>,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val elementChoice = remember {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            UserInfo(account, navController, userViewModel)
            Row(
                Modifier
                    .fillMaxHeight(0.08f)
                    .padding(0.dp, 7.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Elements",
                    modifier = Modifier.clickable { elementChoice.value = !elementChoice.value },
                    fontWeight = FontWeight.Bold,
                    fontSize = 6.em,
                    color =
                    if (elementChoice.value) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = stringResource(R.string.explorations),
                    modifier = Modifier.clickable { elementChoice.value = !elementChoice.value },
                    fontWeight = FontWeight.Bold,
                    fontSize = 6.em,
                    color =
                    if (!elementChoice.value) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }

            if (elementChoice.value) {
                InventoryList(
                    elements = account.elements
                )

            } else {
                if (explorations.isNotEmpty()) {
                    ExplorationList(explorations)
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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(25.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.no_found_explorations),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

    }
}


@Composable
private fun UserInfo(
    account: Account = Account(),
    navController: NavController,
    userViewModel: UserViewModel
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,

                ) {
                Text(text = account.username, fontWeight = FontWeight.Bold, fontSize = 24.sp)
//                val activity = (LocalContext.current as? Activity)
                OutlinedButton(onClick = {
                    deconnexion(navController, userViewModel)

                }) {
                    Text(
                        text = stringResource(R.string.disconnect), style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp),
                color = MaterialTheme.colorScheme.primary,
                thickness = 0.5.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                Card {
                    Row {
                        Text(
                            text = account.inox.toString(),
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
                }
                Card {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.location),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = account.location,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun ExplorationList(
    explorations: List<InfoExploration>, modifier: Modifier = Modifier
) {
    Box(
        modifier
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Exploration(explorations = explorations)
        }
    }
}

@Composable
private fun Exploration(explorations: List<InfoExploration>) {
    LazyColumn(content = {
        items(explorations.sortedByDescending { a -> a.explorationDate }) { exploration ->

            ExplorationCardItem(exploration)
        }
    })
}

@Composable
private fun ExplorationCardItem(exploration: InfoExploration) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(75.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (exploration.affinity.isNotEmpty()) {
                AffinityPicture(exploration.affinity)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Column {
                Text(text = exploration.destination)
                Text(text = formatISODate(exploration.explorationDate))
            }
        }
    }
}


fun deconnexion(navController: NavController, userViewModel: UserViewModel) {

    userViewModel.delete(explorer = Explorer())
    navController.navigate(Screen.Login.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}

