package com.example.gachamobileapp.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gachamobileapp.MainLayout
import com.example.gachamobileapp.ui.theme.GachaMobileAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun HomePage(navController:NavController) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
            ,onClick = { /*TODO*/ }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Player Name", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = "Level: 00")
                }
                CircularProgressIndicator(modifier = Modifier
                    .width(42.dp)
                    .height(42.dp),color = MaterialTheme.colorScheme.secondary, trackColor = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(all = 12.dp)
            ,onClick = { /*TODO*/ }) {
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Row(
                modifier = Modifier.padding(all=12.dp).padding(end = 12.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Epic Chest")
                Text(text = "Epic Chest")
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Row(
                modifier = Modifier.padding(all=12.dp).padding(end = 12.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Epic Chest")
                Text(text = "Rare Chest")
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Row(
                modifier = Modifier.padding(all=12.dp).padding(end = 12.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Epic Chest")
                Text(text = "Common Chest")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeComposable() {
    GachaMobileAppTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = "Home") {
                composable("Home") {
                    MainLayout(
                        navController = navController
                    ) {
                        HomePage(
                            navController = navController
                        )
                    }
                }
                composable("Achievements") {
                    MainLayout(
                        navController = navController
                    ) {
                        HomePage(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}