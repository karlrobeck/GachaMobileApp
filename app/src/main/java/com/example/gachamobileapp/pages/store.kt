package com.example.gachamobileapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gachamobileapp.R

@Composable
fun StorePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .clickable(onClick = { /* Func  */ }),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                    .padding(8.dp)
                    .clickable(onClick = { /* Func */ })
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.___1_),
                        contentDescription = "Daily Free Reward",
                        modifier = Modifier
                            .size(80.dp) // iamage size
                            .padding(end = 8.dp) // space sa text and image
                    )
                    Text(
                        text = "DAILY FREE REWARD",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        // Space adjust
        Spacer(modifier = Modifier.weight(1f))

        // Para sa Images
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StoreItem(
                imageRes = R.drawable.___10_,
                label = "Item 1",
                onClick = { /* Func  */ }
            )
            StoreItem(
                imageRes = R.drawable.___11_,
                label = "Item 2",
                onClick = { /* Func  */ }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StoreItem(
                R.drawable.___3_,
                label = "Item 3",
                onClick = { /* Handle click */ }
            )
            StoreItem(
                R.drawable.___4_,
                label = "Item 4",
                onClick = { /* Handle click */ }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StoreItem(
                R.drawable.___5_,
                label = "Item 5",
                onClick = { /* Handle click */ }
            )
            StoreItem(
                R.drawable.___6_,
                label = "Item 6",
                onClick = { /* Handle click */ }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StoreItem(
                R.drawable.___7_,
                label = "Item 7",
                onClick = { /* Handle click */ }
            )
            StoreItem(
                R.drawable.___8_,
                label = "Item 8",
                onClick = { /* Handle click */ }
            )
        }
    }
}

@Composable
fun StoreItem(imageRes: Int, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                .padding(8.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = label,
                modifier = Modifier
                    .size(160.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = label,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

