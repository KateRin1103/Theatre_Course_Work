package theatre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Spectacle implements Serializable {
    private String nameOfSpectacle;
    private Date dateOfSpectacle;
    private List<Actor> actors;
    private TypeOfSpectacle typeOfSpectacle;
}
