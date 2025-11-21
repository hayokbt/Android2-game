package com.example.soundaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType // ★追加
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // ★追加
import com.example.soundaction.ui.theme.AppTheme
import com.example.soundaction.ui.theme.SoundActionTheme

object FontLoader {
    val googleFontFamily by lazy {
        FontFamily(
            Font(R.font.slaboregular),
        )
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Game : Screen("game")
    data object Result : Screen("result/{combo}/{perfect}/{great}/{good}/{lost}") {
        fun createRoute(combo: Int, perfect: Int, great: Int, good: Int, lost: Int): String {
            return "result/$combo/$perfect/$great/$good/$lost"
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoundActionTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Home.route) {

                    // --- HOME ---
                    composable(Screen.Home.route) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Column(modifier = Modifier.padding(innerPadding)) {
                                MyVerticalLayout(onNavigateToDetails = {
                                    navController.navigate(Screen.Game.route)
                                })
                            }
                        }
                    }

                    // --- GAME ---
                    composable(Screen.Game.route) {
                        // GameScreenに「終わった時の処理」を渡す
                        GameScreen(
                            onGameFinished = { combo, perfect, great, good, lost ->
                                navController.navigate(
                                    Screen.Result.createRoute(combo, perfect, great, good, lost)
                                ) {
                                    popUpTo(Screen.Home.route) { inclusive = false }
                                }
                            }
                        )
                    }

                    // --- RESULT ---
                    composable(
                        route = Screen.Result.route,
                        // arguments = listOf(...) で囲む必要があります
                        arguments = listOf(
                            navArgument("combo") { type = NavType.IntType },
                            navArgument("perfect") { type = NavType.IntType },
                            navArgument("great") { type = NavType.IntType },
                            navArgument("good") { type = NavType.IntType },
                            navArgument("lost") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val combo = backStackEntry.arguments?.getInt("combo") ?: 0
                        val perfect = backStackEntry.arguments?.getInt("perfect") ?: 0
                        val great = backStackEntry.arguments?.getInt("great") ?: 0
                        val good = backStackEntry.arguments?.getInt("good") ?: 0
                        val lost = backStackEntry.arguments?.getInt("lost") ?: 0

                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Column(modifier = Modifier.padding(innerPadding)) {
                                ResultScreen(
                                    combo = combo,
                                    perfect = perfect,
                                    great = great,
                                    good = good,
                                    lost = lost,
                                    onNavigateToDetails = {
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Home.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyVerticalLayout(onNavigateToDetails: () -> Unit) {
    val gradientStart = AppTheme.colors.gradientStart
    val gradientEnd = AppTheme.colors.gradientEnd

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientStart,
                        gradientEnd
                    )
                )
            )
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.TopStart).padding(20.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "設定")
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "Android-two",
                fontSize = 45.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontLoader.googleFontFamily,
            )
            Text(
                text = "🎹",
                fontSize = 150.sp,
            )
            Button(
                onClick = onNavigateToDetails,
                modifier = Modifier.size(width = 150.dp, height = 70.dp)
            ) {
                Text(
                    "START",
                    fontSize = 30.sp,
                    fontFamily = FontLoader.googleFontFamily,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    SoundActionTheme {
        MyVerticalLayout(onNavigateToDetails = {})
    }
}