package adven.geoquiz;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizPresenterTest {

    private static final Question YES_QUESTION = new Question("1", true);

    private QuizPresenter sut;

    @Mock QuestionsService questionsService;
    @Mock QuizContract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new QuizPresenter(questionsService, view);
    }

    @Test
    public void shouldReturnQuestionFromRepoToView() throws Exception {
        final int index = 1;
        final Question expectedQuestion = YES_QUESTION;
        when(questionsService.getQuestion(index)).thenReturn(expectedQuestion);

        sut.getQuestion(index);

        verify(view).showQuestion(expectedQuestion);
    }

    @Test
    public void shouldReturnRightAnswerToView() throws Exception {
        sut.checkAnswer(YES_QUESTION, true);
        verify(view).showCheckResult(true);
    }

    @Test
    public void shouldReturnWrongAnswerToView() throws Exception {
        sut.checkAnswer(YES_QUESTION, false);
        verify(view).showCheckResult(false);
    }
}