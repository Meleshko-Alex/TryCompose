package com.meleha.trycompose.ui.perfectLazyColumn.ui

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meleha.trycompose.R
import com.meleha.trycompose.ui.perfectLazyColumn.model.UsersService

@Preview(showSystemUi = true)
@Composable
fun PerfectLazyColumn(userService: UsersService = UsersService.get()) {
    val userList by userService.getUsers().collectAsStateWithLifecycle()
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        items(
            items = userList,
            key = { user -> user.id }
        ) { user ->
            UserCard(
                user = user,
                onUserClicked = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.clicked_on),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onUserDeleted = {
                    userService.removeUser(user)
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

