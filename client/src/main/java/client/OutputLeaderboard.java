package client;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutputLeaderboard
{
    public List<String> outputLeaderboard(List<LinkedHashMap<String, Object>> scores)
    {
        List<String> output = new ArrayList<>();
        output.add(String.format("%-20s %-20s %-20s %-20s %-20s", "Player", "Correct(%)", "TotalTime(s)", "AverageTime", "StandardDeviationTime"));
        List<String> players = scores.stream()
                .map(score -> score.get("playerName").toString())
                .distinct()
                .collect(Collectors.toList());

        for (String player: players) {
            List<LinkedHashMap<String, Object>> filteredScores = scores.stream()
                    .filter(score -> score.get("playerName").toString().equals(player))
                    .collect(Collectors.toList());

            double correct = 0;
            int totalTime = 0;
            List<Integer> times = new ArrayList<>();
            for (LinkedHashMap<String, Object> filterScore : filteredScores) {
                if (filterScore.get("correct").toString().equals("true")) {
                    correct++;
                }
                totalTime += Integer.parseInt(filterScore.get("responseTime").toString());
            }
            double sd = 0;
            double average = ((double) totalTime / filteredScores.size());
            for(Integer time: times) {
                sd += Math.pow((double)time - average, 2);
            }
            output.add(String.format("%-20s %-20s %-20s %-20s %-20s",
                    player,
                    Math.round((correct/ filteredScores.size()) * 100),
                    totalTime,
                    Math.round(average),
                    Math.round(sd)));
        }
        return output;
    }
}
