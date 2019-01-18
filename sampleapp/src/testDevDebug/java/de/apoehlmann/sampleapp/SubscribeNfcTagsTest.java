package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.NfcRepository;
import de.apoehlmann.sampleapp.data.NfcTag;
import de.apoehlmann.sampleapp.domain.SubscribeNfcTags;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SubscribeNfcTagsTest {

    private SubscribeNfcTags subscribeNfcTags;

    @Mock
    private NfcRepository nfcRepository;

    private final Subject<NfcTag> nfcTagData = PublishSubject.create();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subscribeNfcTags = new SubscribeNfcTags(nfcRepository);

        Mockito.when(nfcRepository.subscribeNfcTags()).thenReturn(nfcTagData);
    }

    @Test
    public void testBuild() {
        Mockito.verifyZeroInteractions(nfcRepository);
    }

    @Test
    public void testSendEvent() throws Exception {
        TestObserver<NfcTag> testObserver = new TestObserver<>();
        subscribeNfcTags.execute(testObserver);

        NfcTag testTag1 = new NfcTag(new byte[0], new String[0]);
        NfcTag testTag2 = new NfcTag(new byte[1], new String[2]);

        nfcTagData.onNext(testTag1);
        nfcTagData.onNext(testTag2);

        testObserver.awaitCount(2);

        testObserver.assertValues(testTag1, testTag2);
    }
}
