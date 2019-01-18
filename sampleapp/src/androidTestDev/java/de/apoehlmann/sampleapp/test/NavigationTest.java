package de.apoehlmann.sampleapp.test;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import de.apoehlmann.sampleapp.action.NavigationActions;
import de.apoehlmann.sampleapp.presentation.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNavigation() {
        NavigationActions.navigateToGreenFragment();
        NavigationActions.navigateToBlueFragment();
        NavigationActions.navigateToGreenFragment();
        NavigationActions.navigateToBlueFragment();
    }
}
