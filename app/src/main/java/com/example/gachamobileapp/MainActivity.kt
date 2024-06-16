package com.example.gachamobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gachamobileapp.pages.Home
import com.example.gachamobileapp.ui.theme.GachaMobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GachaMobileAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout("Home")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(page:String) {
    var currentPage by remember { mutableStateOf("Home") };
    val navigationPages = LinkedHashMap<String,ImageVector>();
    navigationPages.put("Home",Icons.Filled.Home)
    navigationPages.put("Achievements",Icons.Filled.Menu)
    navigationPages.put("Collections",Icons.Filled.List)
    navigationPages.put("Store",Icons.Filled.ShoppingCart)

    Scaffold (
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp),
            ) {
                CenterAlignedTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                },
                actions = {
                    Text(
                        text = "999,999,999"
                    )
                },
                title = { Text(text = "Main Menu") }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ))
            }
        },
        bottomBar = {
            NavigationBar {
                navigationPages.forEach { (name, icon) ->
                    NavigationBarItem(
                        selected = name == currentPage,
                        onClick = {
                            currentPage = name;
                        },
                        icon = { Icon(icon, contentDescription = name) },
                        label = { Text(text=name) })
                }
            }
        }
    ) {
        innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
            when(currentPage) {
                "Home" -> Home()
                "Achievements" -> {}
                "Collections" -> {}
                "Store" -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GachaMobileAppTheme {
        MainLayout("Home")
    }
}