package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.ActivityWrapper;
import de.apoehlmann.sampleapp.data.NfcRepository;
import de.apoehlmann.sampleapp.domain.NfcUseCaseFactory;
import de.apoehlmann.sampleapp.domain.StartNfcListening;
import de.apoehlmann.sampleapp.domain.StopNfcListening;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NfcUseCaseFactoryTest {

    @Mock
    private NfcRepository nfcRepository;

    private ActivityWrapper activityWrapper = new ActivityWrapper(null);

    private NfcUseCaseFactory nfcUseCaseFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nfcUseCaseFactory = new NfcUseCaseFactory(nfcRepository);
    }

    @Test
    public void createStartNfcListening() {
        StartNfcListening firstStartNfcListening = nfcUseCaseFactory.createStartNfcListening(activityWrapper);
        StartNfcListening secondStartNfcListening = nfcUseCaseFactory.createStartNfcListening(activityWrapper);
        Assert.assertNotEquals(firstStartNfcListening, secondStartNfcListening);
    }

    @Test
    public void createStopNfcListening() {
        StopNfcListening firstStopNfcListening = nfcUseCaseFactory.createStopNfcListening(activityWrapper);
        StopNfcListening secondStopNfcListening = nfcUseCaseFactory.createStopNfcListening(activityWrapper);
        Assert.assertNotEquals(firstStopNfcListening, secondStopNfcListening);
    }
}
