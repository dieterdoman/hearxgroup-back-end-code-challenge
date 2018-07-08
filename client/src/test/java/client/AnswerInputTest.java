package client;

import jline.console.ConsoleReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerInputTest
{
    private AnswerInput answerInput;

    @BeforeEach
    void setupTests() throws IOException
    {
        ConsoleReader consoleReader = Mockito.mock(ConsoleReader.class);
        answerInput = new AnswerInput(consoleReader);
        Mockito.when(consoleReader.readLine()).thenReturn("1");
    }

    @Test
    void getAnswerTest() throws IOException
    {
        GameRoundResult gameRoundResult = answerInput.getAnswer(1, 1);
        assertEquals(1, gameRoundResult.getAnswer());
    }
}
