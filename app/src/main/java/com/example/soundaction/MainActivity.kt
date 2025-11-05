package com.example.soundaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundaction.ui.theme.SoundActionTheme
object FontLoader {
    val googleFontFamily by lazy {
        FontFamily(
            Font(R.font.slaboregular),
        )
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoundActionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        MyVerticalLayout()
                    }
                }
            }
        }
    }
}

@Composable
fun MyVerticalLayout() {
    Box(

    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFD19BEA), Color(0xFFA4CCED))
                    )
                )
        ) {

        }
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(100.dp))
            Text(
                "Android-two",
                fontSize = 45.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontLoader.googleFontFamily,
            )
            Spacer(Modifier.height(150.dp))
            Text(
                text = "🎹",
                fontSize = 150.sp,
            )
            Spacer(Modifier.height(150.dp))
            Button(

                onClick = { /* アクション */ },
                modifier = Modifier.size(width = 150.dp, height = 70.dp)
            ) {
                Text(
                    "START",
                    fontSize = 30.sp,
                    fontFamily = FontLoader.googleFontFamily,
                )
            }
        }
        Row(

        ) {
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = {},
                modifier = Modifier.height(100.dp)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "ohaoha")
            }
        }
    }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}