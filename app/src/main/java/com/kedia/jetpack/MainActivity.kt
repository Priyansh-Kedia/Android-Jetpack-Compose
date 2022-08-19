package com.kedia.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kedia.jetpack.ui.theme.JetpackExampleTheme

data class Message(val author: String, val body: String)

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackExampleTheme {
                MyApp()
            }
        }
    }

    @Composable
    private fun MyApp(names: List<String> = listOf("World", "Compose")) {
        var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

        if (shouldShowOnBoarding) {
            OnBoardingScreen(
                onContinueClicked = {
                    shouldShowOnBoarding = false
                }
            )
        } else {
            Greetings()
        }
    }

    @Composable
    fun OnBoardingScreen(onContinueClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome to the Basics Codelab!")
                Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onContinueClicked
                ) {
                    Text("Continue")
                }
            }
        }
    }

    @Composable
    private fun Greetings(names: List<String> = List(1000) { "$it" }) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(names) { name ->
                Greeting(name = name)
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        Card(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name)
        }
    }

    @Composable
    private fun CardContent(name: String) {
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
        }
    }
}
