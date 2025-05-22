package com.example.bookshelf.ui.screens.menu_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelf.R
import com.example.bookshelf.ui.theme.BlueAccent
import com.example.bookshelf.ui.theme.OrangeAccent
import com.example.bookshelf.ui.theme.LightBackground

@Composable
fun MenuScreen(
    onSearchClick: () -> Unit,
    onFavClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground) // Fondo cálido
            .padding(horizontal = 32.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📚 Bienvenido a Bookshelf",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BlueAccent
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onSearchClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueAccent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar libros",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Buscar libros",
                fontSize = 18.sp
            )
        }

        Button(
            onClick = onFavClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangeAccent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Ver favoritos",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Ver favoritos",
                fontSize = 18.sp
            )
        }
    }
}
