package theatre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {
    private int row;
    private int place;
    private String login;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private int returning;
}
