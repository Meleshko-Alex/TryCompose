package com.meleha.trycompose.ui.perfectLazyColumn.model

import com.github.javafaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Random

/**
 * Represents information about users and also provides methods
 * for managing users
 */
interface UsersService {

    /**
     * Get the list of users and listen for all further changes in the user list
     */
    fun getUsers(): StateFlow<List<User>>

    /**
     * Remove the specified user. As a result, the flow returned
     * by [getUsers] will automatically emit an updated list
     */
    fun removeUser(user: User)

    companion object {
        fun get(): UsersService = UsersServiceImpl
    }
}

// --- implementation

private object UsersServiceImpl : UsersService {

    private const val count = 100
    private val faker = Faker.instance(Random(0))
    private val images = mutableListOf(
        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1599566150163-29194dcaad36?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGF2YXRhcnN8ZW58MHx8MHx8fDA%3D",
        "https://plus.unsplash.com/premium_photo-1689551671548-79ff30459d2a?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://plus.unsplash.com/premium_photo-1689533448099-2dc408030f0f?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fGF2YXRhcnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1558898479-33c0057a5d12?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8YXZhdGFyc3xlbnwwfHwwfHx8MA%3D%3D"
    )

    private val usersMutableStateFlow = MutableStateFlow(generateUsers())

    override fun getUsers(): StateFlow<List<User>> {
        return usersMutableStateFlow
    }

    override fun removeUser(user: User) {
        usersMutableStateFlow.update { oldUsers -> oldUsers - user }
    }

    private fun generateUsers() = List(100) { index ->
        val id = index + 1L
        User(
            id = id,
            name = faker.name().fullName(),
            photoUrl = images[index % images.size],
            status = faker.shakespeare().hamletQuote()
        )
    }
}