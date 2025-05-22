package com.example.bookshelf.ui.screens.query_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.screens.components.NothingFoundScreen
import com.example.bookshelf.ui.theme.BlueAccent
import com.example.bookshelf.ui.theme.LightBackground

private const val TAG: String = "Lixo"

@Composable
fun GridList(
    viewModel: QueryViewModel,
    bookshelfList: List<Book>,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit,
) {
    if (bookshelfList.isEmpty()) {
        NothingFoundScreen()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier
                .fillMaxSize()
                .background(LightBackground),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(bookshelfList) { book ->
                GridItem(
                    viewModel = viewModel,
                    book = book,
                    onDetailsClick = onDetailsClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GridItem(
    viewModel: QueryViewModel,
    book: Book,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val isFavorite = viewModel.isBookFavorite(book)

    Card(
        onClick = { onDetailsClick(book) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FavoriteButton(
                    favorite = isFavorite,
                    onFavoriteClick = {
                        if (isFavorite) viewModel.removeFavoriteBook(book)
                        else viewModel.addFavoriteBook(book)
                    }
                )

                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }

            if (expanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "📖 ${book.volumeInfo.title}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    book.volumeInfo.subtitle?.let {
                        Text(
                            text = "📝 $it",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = "✍️ ${book.volumeInfo.allAuthors()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "💰 ${book.saleInfo.getPrice2}",
                        style = MaterialTheme.typography.labelMedium,
                        color = BlueAccent
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    favorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
    IconButton(onClick = onFavoriteClick) {
        Icon(
            imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
            contentDescription = if (favorite) "Eliminar de favoritos" else "Agregar a favoritos",
            tint = if (favorite) Color.Red else Color.Gray
        )
    }
}

@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = if (expanded) "Mostrar menos" else "Mostrar más",
            tint = BlueAccent
        )
    }
}
