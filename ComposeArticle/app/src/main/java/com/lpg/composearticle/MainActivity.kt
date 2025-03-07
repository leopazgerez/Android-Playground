package com.lpg.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lpg.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeArticleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Article(
                        title = stringResource(R.string.jetpack_compose_tutorial),
                        firstParagraph = stringResource(R.string.first_paragraph_article),
                        secondParagraph = stringResource(R.string.second_paragraph_article),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Article(
    title: String,
    firstParagraph: String,
    secondParagraph: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        ImageArticle()
        TitleArticle(title = title)
        FirstParagraphArticle(text = firstParagraph)
        SecondParagraphArticle(text = secondParagraph)
    }
}

@Composable
fun ImageArticle(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.bg_compose_background),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun TitleArticle(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier
            .padding(16.dp),
        text = title,
        textAlign = TextAlign.Start,
        fontSize = 24.sp,
    )
}

@Composable
fun FirstParagraphArticle(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        text = text,
        textAlign = TextAlign.Justify,
        fontSize = 16.sp,
    )
}

@Composable
fun SecondParagraphArticle(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier
            .padding(16.dp),
        text = text,
        textAlign = TextAlign.Justify,
        fontSize = 16.sp,
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeArticleTheme {
        Article(
            title = stringResource(R.string.jetpack_compose_tutorial),
            firstParagraph = stringResource(R.string.first_paragraph_article),
            secondParagraph = stringResource(R.string.second_paragraph_article),
        )
    }
}