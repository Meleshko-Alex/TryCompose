package com.meleha.trycompose.ui.lazyColumn

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleLazyColumn() {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Text(
                text = "Header",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(all = 16.dp)
            )
        }

        stickyHeader {
            Text(
                text = " Sticky Header",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(color = Color.Gray)
                    .fillMaxWidth()
            )
        }

        items(100) {index ->
            Text(
               text = "Simple text item #${index + 1}",
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SimpleLazyColumnPreview() {
    SimpleLazyColumn()
}