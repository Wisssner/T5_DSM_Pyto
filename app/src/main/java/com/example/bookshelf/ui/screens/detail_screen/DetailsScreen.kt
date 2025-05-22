package com.example.bookshelf.ui.screens.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.screens.components.ErrorScreen
import com.example.bookshelf.ui.screens.components.LoadingScreen
import com.example.bookshelf.ui.theme.BlueAccent
import com.example.bookshelf.ui.theme.LightBackground

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
    retryAction: () -> Unit,
) {
    val uiStateDet = viewModel.uiStateDetail.collectAsState().value

    when (uiStateDet) {
        is DetailsUiState.Loading -> LoadingScreen()
        is DetailsUiState.Error -> ErrorScreen(retryAction = retryAction)
        is DetailsUiState.Success -> BookDetails(book = uiStateDet.bookItem)
    }
}

@Composable
fun BookDetails(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = BlueAccent
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https"))
                .crossfade(true)
                .build(),
            contentDescription = book.volumeInfo.title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.65f),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
        )

        Spacer(modifier = Modifier.height(24.dp))

        InfoSection(label = "Subtítulo:", value = book.volumeInfo.subtitle)
        InfoSection(label = "Autores:", value = book.volumeInfo.allAuthors())
        InfoSection(label = "Precio:", value = book.saleInfo.getPrice2)
        InfoSection(label = "País:", value = book.saleInfo.country)
        InfoSection(label = "Descripción:", value = book.volumeInfo.description)
    }
}

@Composable
fun InfoSection(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = BlueAccent
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
