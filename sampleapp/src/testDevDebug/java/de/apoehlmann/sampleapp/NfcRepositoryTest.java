package de.apoehlmann.sampleapp;

import de.apoehlmann.sampleapp.data.ActivityWrapper;
import de.apoehlmann.sampleapp.data.NfcAdapterWrapper;
import de.apoehlmann.sampleapp.data.NfcRepositoryImpl;
import de.apoehlmann.sampleapp.data.NfcTag;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class NfcRepositoryTest {

    private NfcRepositoryImpl nfcRepositoryImpl;
    @Mock
    private NfcAdapterWrapper nfcAdapterWrapper;
    @Mock
    private Consumer<NfcTag> testConsumer;

    PublishSubject<NfcTag> nfcTagsData = PublishSubject.create();
    ActivityWrapper activityWrapper = new ActivityWrapper(null);


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nfcRepositoryImpl = new NfcRepositoryImpl(nfcAdapterWrapper);
//        Mockito.doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                return null;
//            }
//        }).when(nfcAdapterWrapper).startListening(activityWrapper, Mockito.any(Subject.class));
    }

    @Test
    public void testStartAndStop() {
        nfcRepositoryImpl.startListening(activityWrapper);

        Mockito.verify(nfcAdapterWrapper, Mockito.times(1)).startListening(activityWrapper, nfcRepositoryImpl.getNfcListener().getValue());

        nfcRepositoryImpl.stopListening(activityWrapper);
        Mockito.verify(nfcAdapterWrapper, Mockito.times(1)).stopListening(activityWrapper);
    }

    @Test
    public void testListening() throws Exception {
        nfcRepositoryImpl.startListening(activityWrapper);
        Mockito.verify(nfcAdapterWrapper, Mockito.times(1)).startListening(activityWrapper, nfcRepositoryImpl.getNfcListener().getValue());

        Consumer<NfcTag> testConsumer = Mockito.mock(Consumer.class);
        nfcRepositoryImpl.subscribeNfcTags().subscribe(testConsumer);

        NfcTag testNfcTag = new NfcTag(new byte[0], new String[0]);

        nfcRepositoryImpl.getNfcListener().getValue().onNext(testNfcTag);

        Mockito.verify(testConsumer, Mockito.times(1)).accept(testNfcTag);

        nfcRepositoryImpl.stopListening(activityWrapper);
        Mockito.verify(nfcAdapterWrapper, Mockito.times(1)).stopListening(activityWrapper);
    }
}
