package com.santhosh.core.navigation

interface AppNavigationDestination {
    /**
     *  A route serves as a unique identifier for a destination within the navigation graph.
     *  It functions similarly to a URL, mapping to a specific destination and enabling
     *  the NavController to navigate to that destination. Routes can also incorporate arguments,
     *  allowing data to be passed between destinations during navigation.
     */
    val route: String

    /**
     * A destination represents a single screen or a distinct part of your application's UI
     * that a user can navigate to. Each destination in your navigation graph is assigned a
     * unique identifier, often a route string
     */
    val destination: String
}