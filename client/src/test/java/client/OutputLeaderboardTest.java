package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputLeaderboardTest
{
    private OutputLeaderboard outputLeaderboard;

    @BeforeEach
    void setupTests()
    {
        outputLeaderboard = new OutputLeaderboard();
    }

    @Test
    void outputLeaderboardTest()
    {
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add(String.format("%-20s %-20s %-20s %-20s %-20s", "Player", "Correct(%)", "TotalTime(s)", "AverageTime", "StandardDeviationTime"));
        expectedOutput.add(String.format("%-20s %-20s %-20s %-20s %-20s", "dieter", 100, 1, 1, 0));

        LinkedHashMap<String, Object> rounds = Mockito.mock(LinkedHashMap.class);
        Mockito.when(rounds.get("playerName")).thenReturn("dieter");
        Mockito.when(rounds.get("correct")).thenReturn(true);
        Mockito.when(rounds.get("responseTime")).thenReturn(1);

        assertEquals(expectedOutput, outputLeaderboard.outputLeaderboard(Collections.singletonList(rounds)));
    }

}
