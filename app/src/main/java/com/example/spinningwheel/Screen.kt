package com.example.spinningwheel

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object WinScreen : Screen ("win_screen")
    object LoseScreen: Screen("lose_screen")
    }

