package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.NfcTag;
import de.apoehlmann.sampleapp.presentation.NfcTagMapper;
import org.junit.Assert;
import org.junit.Test;

import static de.apoehlmann.sampleapp.presentation.NfcTagMapper.EMPTY_ID;

public class NfcTagMapperTest {

    @Test
    public void testIdAndTechListAreNull() {
        try {
            NfcTag tag = new NfcTag(null, null);
            NfcTagMapper.Companion.transform(tag);
            Assert.assertTrue("A IllegalArgumentException should be throw", true);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testNfcTagWithNull() {
        try {
            NfcTagMapper.Companion.transform(null);
            Assert.assertTrue("A IllegalArgumentException should be throw", true);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testEmptyIdAndEmptyTechList() {
        String expectedText = EMPTY_ID;

        NfcTag tag = new NfcTag(new byte[0], new String[0]);
        String actualText = NfcTagMapper.Companion.transform(tag);

        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testWithIdAndTechList() {
        byte[] id = {34, 2, 54, 12};
        String[] techList = {"asdf", "dasf"};

        String expectedText = "[34, 2, 54, 12]\n" + "asdf , dasf ";

        NfcTag tag = new NfcTag(id, techList);
        String actualText = NfcTagMapper.Companion.transform(tag);

        Assert.assertEquals(expectedText, actualText);
    }
}
