package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.ActivityWrapper;
import de.apoehlmann.sampleapp.data.NfcRepository;
import de.apoehlmann.sampleapp.domain.StopNfcListening;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StopNfcListeningTest {

    private StopNfcListening stopNfcListening;

    @Mock
    private NfcRepository nfcRepository;
    private ActivityWrapper activityWrapper = new ActivityWrapper(null);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(nfcRepository.stopListening(activityWrapper)).thenReturn(Observable.<Void>empty());

        stopNfcListening = new StopNfcListening(nfcRepository, activityWrapper);
    }

    @Test
    public void testBuild() {
        Mockito.verifyZeroInteractions(nfcRepository);
    }

    @Test
    public void testExecute() throws Exception {
        Consumer<Void> onNextTestConsumer = Mockito.mock(Consumer.class);
        Consumer<Throwable> onErrorTestConsumer = Mockito.mock(Consumer.class);
        Action onCompleteTestAction = Mockito.mock(Action.class);
        stopNfcListening.execute(onNextTestConsumer, onErrorTestConsumer, onCompleteTestAction);

        Mockito.verifyZeroInteractions(onNextTestConsumer);
        Mockito.verifyZeroInteractions(onErrorTestConsumer);
        Mockito.verify(onCompleteTestAction, Mockito.times(1)).run();

    }
}
