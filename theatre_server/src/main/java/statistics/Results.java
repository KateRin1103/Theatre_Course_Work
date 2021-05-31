package statistics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Results extends Statistics {
    private String title;
    private double firstMonthEffect;
    private double secondMonthEffect;
    private double thirdMonthEffect;
    private double risk;
}
