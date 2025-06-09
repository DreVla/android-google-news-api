package com.example.gnewsapi.ui.screen.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gnewsapi.R
import kotlin.random.Random

@Composable
fun EasterEgg() {
    val context = LocalContext.current

    // I just think they're neat
    // [source](https://media1.tenor.com/m/eHIRFWRKeQoAAAAd/marge-i-just-think-theyre-neat.gif)
    val showEasterEgg = Random.nextInt(0, 10)
    if (showEasterEgg >= 8) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Icon(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        Toast.makeText(context, "You found me!", Toast.LENGTH_SHORT).show()
                    },
                painter = painterResource(R.drawable.outline_android_24),
                contentDescription = "Easter egg",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            )
        }
    }
}