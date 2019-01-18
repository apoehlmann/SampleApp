package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.ActivityWrapper;
import de.apoehlmann.sampleapp.data.NfcRepository;
import de.apoehlmann.sampleapp.domain.StartNfcListening;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.BaseTestConsumer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StartNfcListeningTest {

    private StartNfcListening startNfcListening;

    @Mock
    private NfcRepository nfcRepository;
    private ActivityWrapper activityWrapper = new ActivityWrapper(null);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        startNfcListening = new StartNfcListening(nfcRepository, activityWrapper);
        Mockito.when(nfcRepository.startListening(activityWrapper)).thenReturn(Observable.<Void>empty());
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
        startNfcListening.execute(onNextTestConsumer, onErrorTestConsumer, onCompleteTestAction);


        Mockito.verifyZeroInteractions(onNextTestConsumer);
        Mockito.verifyZeroInteractions(onErrorTestConsumer);
        Mockito.verify(onCompleteTestAction, Mockito.times(2)).run();

    }
}
