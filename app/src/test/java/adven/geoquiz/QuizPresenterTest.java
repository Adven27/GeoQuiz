package adven.geoquiz;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizPresenterTest {

    private static final int YES_Q_INDEX = 1;
    private static final Question YES_QUESTION = new Question("1", true);

    private QuizPresenter sut;
    @Mock QuestionsService questionsService;
    @Mock QuizContract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(questionsService.getQuestion(YES_Q_INDEX)).thenReturn(YES_QUESTION);
        sut = new QuizPresenter(questionsService, view);
    }

    @Test
    public void shouldReturnQuestionFromRepoToView() throws Exception {
        sut.getQuestion(YES_Q_INDEX);
        verify(view).showQuestion(YES_QUESTION);
    }

    @Test
    public void shouldReturnRightAnswerToView() throws Exception {
        sut.checkAnswer(YES_Q_INDEX, true);
        verify(view).showCheckResult(true);
    }

    @Test
    public void shouldReturnWrongAnswerToView() throws Exception {
        sut.checkAnswer(YES_Q_INDEX, false);
        verify(view).showCheckResult(false);
    }

    @Test
    public void shouldShowCheatScreen() throws Exception {
        sut.showCheatScreen(YES_Q_INDEX);
        verify(view).showCheatScreen(YES_QUESTION.isCorrect());
    }

    @Test(expected = QuizContract.UserActionsListener.QuestionNotFoundException.class)
    public void shouldThrowExceptionIfQuestionNotFound() throws Exception {
        sut.getQuestion(100);
    }
}