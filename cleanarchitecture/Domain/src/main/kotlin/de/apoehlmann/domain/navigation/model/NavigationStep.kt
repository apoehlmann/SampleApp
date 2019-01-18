package de.apoehlmann.domain2.navigation.model

data class NavigationStep(val key: String) {
    companion object {
        var i = 0;
        fun generateNewNavigationStep(): NavigationStep {
            val navigationStep = NavigationStep("Step #$i")
            i += 1
            return navigationStep
        }
    }
}