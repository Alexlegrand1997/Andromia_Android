package ca.qc.cstj.andromia.ui.screens.booster

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.dto.BoosterDTO
import ca.qc.cstj.andromia.models.Booster
import ca.qc.cstj.andromia.ui.composables.ChestPicture
import ca.qc.cstj.andromia.ui.composables.InventoryList
import ca.qc.cstj.andromia.ui.composables.LoadingSpinner


// https://stackoverflow.com/questions/68694031/jetpack-compose-passing-data-from-lazy-column-into-another-composable

@Composable
fun BoosterScreen(navController: NavController, boosterViewModel: BoosterViewModel = viewModel()) {
    val boosterUIState by boosterViewModel.boosterUIState.collectAsState()

    when (val state = boosterUIState) {
        is BoosterUIState.Error -> Toast.makeText(
            LocalContext.current,
            state.exception.message,
            Toast.LENGTH_LONG
        ).show()

        BoosterUIState.Loading -> LoadingSpinner()
        is BoosterUIState.Success -> {
            if (state.account.boosters.isNotEmpty()) {
                BoosterCard(state.account.boosters, boosterViewModel)
            } else {
                AucunBooster()
            }
        }

        is BoosterUIState.BoosterBuyed -> {
            BoosterContentCard(state.boosterDTO, boosterViewModel)
        }
    }
}

@Composable
fun BoosterContentCard(dto: BoosterDTO, boosterViewModel: BoosterViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = stringResource(R.string.mainbackground),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(20.dp),
        ) {
            Card {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.inox) + dto.loot.inox,
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider(color = Color.White, modifier = Modifier.height(1.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = stringResource(R.string.new_elements),
                                fontSize = 15.sp,
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            InventoryList(
                                elements = dto.loot.elements, modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { boosterViewModel.refreshBoosters(account = dto.explorateur) }) {
                            Text(
                                text = stringResource(id = R.string.confirm), style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoosterCard(
    boosters: List<Booster>,
    boosterViewModel: BoosterViewModel,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                BoosterList(boosters = boosters, boosterViewModel)
            }
        }
    }
}

@Preview
@Composable
private fun AucunBooster() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(modifier = Modifier.padding(20.dp)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(25.dp)
                ) {
                    Text(text = stringResource(R.string.no_booster_avaialble_for_the_moment))
                }
            }
        }
    }
}

@Composable
private fun BoosterList(boosters: List<Booster>, boosterViewModel: BoosterViewModel) {
    var selectedBooster by remember {
        mutableStateOf<Booster?>(boosters[0])
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Transparent)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.80f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    selectedBooster?.let {
                        LockedBooster(booster = it, boosterViewModel)
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    items(boosters) { booster ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    selectedBooster = booster
                                },
                            shape = RectangleShape,
                            colors = CardDefaults.cardColors(Color.Transparent)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.background(
                                        Color.Transparent
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        ChestPicture(
                                            imageName = booster.rarity,
                                            modifier = Modifier.fillMaxSize(0.80f)
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = booster.rarity,
                                            style = TextStyle(
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily.Monospace,
                                                color = Color.White,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun LockedBooster(booster: Booster, boosterViewModel: BoosterViewModel) {
    Spacer(modifier = Modifier.fillMaxHeight(0.10f))
    Row(
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .background(Color.Transparent)
    ) {
        ChestPicture(imageName = booster.rarity)
    }
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Transparent)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = booster.rarity,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(0.dp, 10.dp)
            )
            Text(
                text = "Price: ${booster.price}",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(0.dp, 10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = { boosterViewModel.buyBooster(booster.openMe, booster.price) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.buy), textAlign = TextAlign.Center, style = TextStyle(
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