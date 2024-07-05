package ca.qc.cstj.andromia.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import java.util.Locale

@Composable
fun getPicture(imageName:String, modifier:Modifier=Modifier) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName,modifier)
}
@Composable
fun ElementPicture(imageName:String) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("element_$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName)
}

@Composable
fun AffinityPicture(imageName:String, modifier:Modifier=Modifier) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName,modifier)
}

@Composable
fun BookPicture(imageName:String, modifier:Modifier=Modifier) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName,modifier)
}

@Composable
fun ChestPicture(imageName:String, modifier:Modifier=Modifier) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("chest_$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName, modifier)
}

@Composable
fun LockedChestPicture(imageName:String, modifier:Modifier=Modifier) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("chest_locked_$imageName".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageName, modifier)
}

@Composable
fun RandomLoadingImg(imageNumber:String = "1") {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("loading_$imageNumber".lowercase(), "drawable", context.packageName)
    Image(painter = painterResource(id = imageId), contentDescription = imageNumber, contentScale = ContentScale.Crop)
}