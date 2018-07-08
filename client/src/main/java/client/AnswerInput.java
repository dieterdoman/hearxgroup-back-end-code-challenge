package client;

import com.google.common.base.Stopwatch;
import jline.console.ConsoleReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class AnswerInput
{
    private ConsoleReader consoleReader;

    public AnswerInput(ConsoleReader consoleReader)
    {
        this.consoleReader = consoleReader;
    }

    public GameRoundResult getAnswer(Integer number1, Integer number2) throws IOException
    {
        consoleReader.setPrompt(MessageFormat.format("{0} x {1} = ", number1, number2));

        Stopwatch stopwatch = Stopwatch.createStarted();
        int answer = Integer.parseInt(consoleReader.readLine());
        stopwatch.stop();

        GameRoundResult gameRoundResult = new GameRoundResult();
        gameRoundResult.setAnswer(answer);
        gameRoundResult.setTime((int) (stopwatch.elapsed(MILLISECONDS) / 1000) % 60 );
        return gameRoundResult;
    }
}
