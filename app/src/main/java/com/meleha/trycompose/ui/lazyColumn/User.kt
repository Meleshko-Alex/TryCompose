package com.meleha.trycompose.ui.lazyColumn

import com.github.javafaker.Faker
import java.util.Random

data class User(
    val id: Long,
    val name: String,
    val photoUrl: String,
    val status: String
)

fun createUserList(): List<User> {
    val faker = Faker.instance(Random(0))
    val images = mutableListOf(
        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1599566150163-29194dcaad36?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGF2YXRhcnN8ZW58MHx8MHx8fDA%3D",
        "https://plus.unsplash.com/premium_photo-1689551671548-79ff30459d2a?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://plus.unsplash.com/premium_photo-1689533448099-2dc408030f0f?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fGF2YXRhcnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1558898479-33c0057a5d12?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D"
    )
    val list = List(100) { index ->
        val id = index + 1L
        User(
            id = id,
            name = faker.name().fullName(),
            photoUrl = images[index % images.size],
            status = faker.shakespeare().hamletQuote()
        )
    }
    return list
}
