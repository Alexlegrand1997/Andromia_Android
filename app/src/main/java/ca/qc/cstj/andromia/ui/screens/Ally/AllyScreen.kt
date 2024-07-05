package ca.qc.cstj.andromia.ui.screens.Ally

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ca.qc.cstj.andromia.R
import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.ui.composables.AffinityPicture
import ca.qc.cstj.andromia.ui.composables.BookPicture
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest


@Composable
fun AllyScreen(navController: NavController, allyViewModel: AllyViewModel = viewModel()) {
    val allyUIState by allyViewModel.allyUIState.collectAsState()

    when (val state = allyUIState) {
        is AllyUIState.Error -> Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT)
            .show()

        AllyUIState.Loading -> Unit
        is AllyUIState.Success ->
            if (state.ally.isNotEmpty()) {
                AllyCard(ally = state.ally)

            } else {
                AucunAlly()
            }
    }
}

@Composable
private fun AllyCard(ally: List<Ally>) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InventoryList(
                    allies = ally
                )
            }
        }

    }
}

@Composable
fun InventoryList(
    allies: List<Ally>, modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
) {
    Box(
        modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Inventory(allies = allies)
        }
    }
}

@Composable
private fun Inventory(allies: List<Ally>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(allies) { ally ->
            if (ally.uuid.isNotEmpty()) {
                AllyCardItem(ally)
            }
        }
    })
}


@Composable
fun AllyCardItem(ally: Ally, modifier: Modifier = Modifier) {
    var currentAllyInfo = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(3.dp)
            .clickable {
                currentAllyInfo.value = true
            }
            .border(2.dp, MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(5.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
//            ElementPicture(element.element)
//            https://coil-kt.github.io/coil/compose/

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ally.asset)
                        .crossfade(true)
                        .build(),
                    contentDescription = ally.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape),
                    loading = {
                        CircularProgressIndicator()
                    }, error = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_image_not_supported_24),
                            contentDescription = stringResource(R.string.picture_unavailable),
                            Modifier
                                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                )
                Text(text = ally.name, fontWeight = FontWeight.Bold)

                if (currentAllyInfo.value) {
                    ExtraInfoAlly(
                        onDismissRequest = { },
                        ally = ally,
                        currentAllyInfo
                    )
                }
            }
        }
    }
}

@Composable
fun ExtraInfoAlly(
    onDismissRequest: () -> Unit,
    ally: Ally,
    currentAllyInfo: MutableState<Boolean>
) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(2.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = ally.name,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ally.asset)
                        .crossfade(true)
                        .build(),

                    contentDescription = ally.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape),
                    loading = {
                        CircularProgressIndicator()
                    }, error = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_image_not_supported_24),
                            contentDescription = stringResource(R.string.picture_unavailable),
                            Modifier
                                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }

                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp)
                        .padding(0.dp, 15.dp, 0.dp, 0.dp),

                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    BookPicture(ally.books[0])
                    AffinityPicture(imageName = ally.affinity)
                    BookPicture(ally.books[1])
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(0.dp, 15.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.power),
                            contentDescription = stringResource(R.string.power),
                            modifier = Modifier

                                .height(30.dp)
                        )
                        Text(
                            text = ally.stats.power.toString(),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.speed),
                            contentDescription = stringResource(R.string.speed),
                            modifier = Modifier

                                .height(30.dp)
                        )
                        Text(
                            text = ally.stats.speed.toString(),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.shield),
                            contentDescription = stringResource(R.string.shield),
                            modifier = Modifier

                                .height(30.dp)
                        )
                        Text(
                            text = ally.stats.shield.toString(),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = stringResource(R.string.life),
                            modifier = Modifier

                                .height(30.dp)
                        )
                        Text(
                            text = ally.stats.life.toString(),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest();currentAllyInfo.value = false },
                        modifier = Modifier.padding(8.dp),
                    ) {

                        Text(
                            stringResource(R.string.confirm), style = TextStyle(
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
    }
}
@Composable
private fun AucunAlly() {
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
                    Text(text = stringResource(R.string.NoAlly))
                }
            }
        }
    }
}