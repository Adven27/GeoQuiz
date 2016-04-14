package adven.geoquiz;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class CheatPresenterTest {

    private CheatPresenter sut;
    @Mock CheatContract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new CheatPresenter(view);
    }

    @Test
    public void shouldShowAnswer() throws Exception {
        sut.showAnswer(true);
        verify(view).showAnswer(true);
    }
}