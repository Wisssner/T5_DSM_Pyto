package com.example.bookshelf

enum class AppDestinations(val title: String) {
    MenuScreen(title = "BookApp"),
    QueryScreen(title = "Google Bookshelf"),
    FavoriteScreen(title = "Mis libros favoritos"),
    DetailScreen(title = "Libro: ")
}