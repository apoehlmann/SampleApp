package de.apoehlmann.domain2.navigation.model

interface Navigator {
    fun supportNavigationStep(navigationStep: NavigationStep): Boolean
    fun navigate(navigationStep: NavigationStep)
}