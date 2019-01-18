package de.apoehlmann.sampleapp.action;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import de.apoehlmann.sampleapp.R;

public class NavigationActions {

    public static void navigateToBlueFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_home)).perform(ViewActions.click());
    }

    public static void navigateToGreenFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_notifications)).perform(ViewActions.click());
    }
}
