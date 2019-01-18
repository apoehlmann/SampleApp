package de.apoehlmann.sampleapp.action;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import de.apoehlmann.sampleapp.R;
import de.apoehlmann.sampleapp.data.NfcAdapterWrapperImpl;
import de.apoehlmann.sampleapp.data.NfcTag;
import de.apoehlmann.sampleapp.presentation.NfcTagMapper;
import org.hamcrest.Description;
import org.hamcrest.Matchers;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class NfcActions {

    public static void injectNfcTag(NfcTag nfcTag, Activity activity) {
        Intent intent = new Intent();
        intent.setAction(NfcAdapterWrapperImpl.ACTION);
        intent.putExtra(NfcAdapterWrapperImpl.KEY_ID, nfcTag.getId());
        intent.putExtra(NfcAdapterWrapperImpl.KEY_TECHLIST, nfcTag.getTechList());
        activity.sendBroadcast(intent);
    }

    public static void verifyBlueFragment(NfcTag nfcTag) {
        Espresso.onView(withId(R.id.title)).check(matches(withText(R.string.tag_title)));
        Espresso.onView(withId(R.id.latestTag)).check(matches(withText(NfcTagMapper.Companion.transform(nfcTag))));
    }

    public static void verifyGreenFragment(List<NfcTag> nfcTags) {
        for (int i = 0; i < nfcTags.size(); i++) {
            onData(instanceOf(String.class))
                    .inAdapterView(withId(R.id.tag_list))
                    .atPosition(i)
                    .check(matches((withText(NfcTagMapper.Companion.transform(nfcTags.get(i))))));
        }

    }
}
