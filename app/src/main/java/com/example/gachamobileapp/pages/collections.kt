package com.example.gachamobileapp.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.gachamobileapp.R

@Composable
fun CollectionsPage(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .height(56.dp),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Cardrow(
                images = listOf(
                    R.drawable.___10_,
                    R.drawable.___11_,
                    R.drawable.___12_,
                    R.drawable.___13_
                ),
                labels = listOf("Card 1", "Card 2", "Card 3", "Card 4")
            )
            Cardrow(
                images = listOf(
                    R.drawable.___14_,
                    R.drawable.___16_,
                    R.drawable.___17_,
                    R.drawable.___23_
                ),
                labels = listOf("Card 5", "Card 6", "Card 7", "Card 8")
            )

            Cardrow(
                images = listOf(
                    R.drawable.___22_,
                    R.drawable.___28_,
                    R.drawable.___24_,
                    R.drawable.___25_
                ),
                labels = listOf("Card 9", "Card 10", "Card 11", "Card 12")
            )

            Cardrow(
                images = listOf(
                    R.drawable.___2_,
                    R.drawable.___31_,
                    R.drawable.___30_,
                    R.drawable.___3_
                ),
                labels = listOf("Card 13", "Card 14", "Card 15", "Card 16")
            )

            Cardrow(
                images = listOf(
                    R.drawable.___4_,
                    R.drawable.___5_,
                    R.drawable.___6_,
                    R.drawable.___7_
                ),
                labels = listOf("Card 17", "Card 18", "Card 19", "Card 20")
            )
        }
    }
}

@Composable
fun Cardrow(images: List<Int>, labels: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        images.forEachIndexed { index, image ->
            Card(image = image, label = labels.getOrNull(index) ?: "")
        }
    }
}

@Composable
fun Card(image: Int, label: String) {
    val painter = rememberImagePainter(image)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = label,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = Color.Black
        )
    }
}