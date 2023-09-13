package com.example.composestart

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composestart.ui.theme.ComposeStartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(viewModel: MainViewModel = hiltViewModel()) {
    var textInput by rememberSaveable { mutableStateOf("") }
    val words = viewModel.words.collectAsState().value

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            Modifier.weight(1f)
        ) {
            itemsIndexed(items = words) { index, item ->
                cardElement(index = index, item, callback = {
                    textInput = ""
                })
            }
        }
        Row {
            OutlinedTextField(
                value = textInput,
                onValueChange = {
                    textInput = it
                },
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {
                    viewModel.insert(textInput)
                    textInput = ""
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun cardElement(index: Int, item: Note, callback: () -> Unit, viewModel: MainViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier.fillMaxSize().pointerInput(Unit){
            detectTapGestures(
                onLongPress = {
                    viewModel.delete(index)
                    callback()
                }
            )
        }
    ) {
        Text(
            modifier = Modifier.width(150.dp).height(75.dp),
            text = AnnotatedString(item.text),
            style = TextStyle(fontSize = 18.sp),
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                viewModel.setFavorite(index)
            }
        ) {
            Icon(
                painter = painterResource(if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border),
                contentDescription = null,
            )
        }
    }
    Divider()
}