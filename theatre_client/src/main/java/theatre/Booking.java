package theatre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {
    private int row;
    private int place;
    private String username;
    private String spectacle;
}
