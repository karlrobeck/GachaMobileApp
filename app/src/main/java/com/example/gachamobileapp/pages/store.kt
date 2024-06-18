package com.example.gachamobileapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun StorePage(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        Text(text = "Hello from Store")
    }
}