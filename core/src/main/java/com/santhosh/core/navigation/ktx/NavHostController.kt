package com.santhosh.core.navigation.ktx

import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

fun NavHostController.addOnDestinationChangedListener(
    listener: (destination: NavDestination, arguments: Bundle?) -> Unit
) {
    addOnDestinationChangedListener { _, destination, arguments ->
        listener(destination, arguments)
    }
}