package theatre;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Place implements Serializable {
    private int row;
    private int place;
}
