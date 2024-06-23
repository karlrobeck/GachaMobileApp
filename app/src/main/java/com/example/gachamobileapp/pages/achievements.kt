package com.example.gachamobileapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.gachamobileapp.R
import com.example.gachamobileapp.ui.theme.GachaMobileAppTheme

@Composable
fun AchievementsPage(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .height(56.dp),
            shape = MaterialTheme.shapes.medium
        )

        TextHeaderAndImageHolder("NEGGA Achievements", listOf(
            R.drawable.___10_,
            R.drawable.___11_,
            R.drawable.___12_,
            R.drawable.___13_
        ))

        TextHeaderAndImageHolder("SKIBIDI Achievements", listOf(
            R.drawable.___14_,
            R.drawable.___16_,
            R.drawable.___17_
        ))

        TextHeaderAndImageHolder("GYAT Achievements", listOf(
            R.drawable.___18_,
            R.drawable.___19_,
            R.drawable.___1_,
            R.drawable.___20_
        ))

        TextHeaderAndImageHolder("RIZZ Achievements", listOf(
            R.drawable.___22_,
            R.drawable.___23_,
            R.drawable.___24_,
            R.drawable.___25_,
            R.drawable.___25_
        ))

        TextHeaderAndImageHolder("FANUMTAX Achievements", listOf(
            R.drawable.___26_,
            R.drawable.___28_,
            R.drawable.___29_,
            R.drawable.___30_
        ))
    }
}

@Composable
fun TextHeaderAndImageHolder(headerText: String, images: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = headerText,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp) // Adjust as needed
                .padding(horizontal = 8.dp, vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(images) { index, image ->
                ImageHolder(image = image)
                if (index < images.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun ImageHolder(image: Int) {
    val painter = rememberAsyncImagePainter(image)

    Box(
        modifier = Modifier
            .size(70.dp)
            .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AchievementsPagePreview() {
    GachaMobileAppTheme {
        GachaMobileAppTheme {

        }
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}