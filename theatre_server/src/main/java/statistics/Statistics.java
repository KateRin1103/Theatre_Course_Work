package statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistics {
    private String title;
    private LocalDate date;
    private int numberOfShows;
    private int goal;
    private int result;
}
