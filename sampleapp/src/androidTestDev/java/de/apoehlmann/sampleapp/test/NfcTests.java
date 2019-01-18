package de.apoehlmann.sampleapp.test;

import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import de.apoehlmann.sampleapp.action.NavigationActions;
import de.apoehlmann.sampleapp.action.NfcActions;
import de.apoehlmann.sampleapp.data.NfcTag;
import de.apoehlmann.sampleapp.presentation.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NfcTests {

    private final byte[] tag1Id = new byte[] {34, 12, 34};
    private final byte[] tag2Id = new byte[] {54, 3, 56};
    private final byte[] tag3Id = new byte[] {21, 43, 90};
    private final String[] techList1 = new String[] {"NfcA", "Ndef"};
    private final String[] techList2 = new String[] {"NfcB", "NdefFormatable"};
    private final String[] techList3 = new String[] {"NfcC"};
    private final NfcTag tag1 = new NfcTag(tag1Id, techList1);
    private final NfcTag tag2 = new NfcTag(tag2Id, techList2);
    private final NfcTag tag3 = new NfcTag(tag3Id, techList3);

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNfcOnBlueFragment() throws InterruptedException {
        NavigationActions.navigateToBlueFragment();
        NfcActions.injectNfcTag(tag1, rule.getActivity());
        Thread.sleep(300);
        NfcActions.verifyBlueFragment(tag1);
    }

    @Test
    public void testNfcOnGreenFragment() throws InterruptedException {
        NavigationActions.navigateToBlueFragment();
        NavigationActions.navigateToGreenFragment();
        NfcActions.injectNfcTag(tag1, rule.getActivity());
        NfcActions.injectNfcTag(tag2, rule.getActivity());
        NfcActions.injectNfcTag(tag3, rule.getActivity());
        Thread.sleep(300);
        NfcActions.verifyGreenFragment(Lists.newArrayList(tag1, tag2, tag3));
    }
}
