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
public class Seance implements Serializable {
    private String film;
    private LocalDate date;
    private LocalTime time;
    private int price;
}
