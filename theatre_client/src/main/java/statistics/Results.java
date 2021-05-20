package statistics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Results {
    private String title;
    private double firstMonthEffect;
    private double secondMonthEffect;
    private double thirdMonthEffect;
    private double risk;
}
