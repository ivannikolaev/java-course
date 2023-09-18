package org.game;

import org.game.question.Question;
import org.game.question.generation.QuestionGenerationStrategy;

import java.util.Map;

public class Board {
    private static final int BOARD_FIELDS_COUNT = 12;

    private final Map<Integer, QuestionGenerationStrategy> moduloToProvider;

    public Board(Map<Integer, QuestionGenerationStrategy> moduloToProvider) {
        this.moduloToProvider = moduloToProvider;
    }

    public Question getQuestion(int boardPosition) {
        validatePlayerPosition(boardPosition);

        int themesCount = moduloToProvider.keySet().size();
        int key = boardPosition % themesCount;

        return getQuestionStrategy(key)
                .getQuestion();
    }

    // вынес сюда, поскольку может быть неочевидно (ловушки, змейки, лестницы)
    public int playerNewPosition(int currentPosition, int userStep) {
        return (currentPosition + userStep) % BOARD_FIELDS_COUNT;
    }

    private void validatePlayerPosition(int boardPosition) {
        if (boardPosition > BOARD_FIELDS_COUNT) {
            String message = "Board position can't be greater than %s!".formatted(BOARD_FIELDS_COUNT);
            throw new IllegalArgumentException(message);
        }
    }

    private QuestionGenerationStrategy getQuestionStrategy(int key) {
        QuestionGenerationStrategy questionGenerationStrategy = moduloToProvider.get(key);
        if (questionGenerationStrategy == null) {
            throw new IllegalArgumentException("Incorrect filling of question themes provider!");
        }

        return questionGenerationStrategy;
    }
}
