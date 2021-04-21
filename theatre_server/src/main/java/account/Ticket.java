package account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Ticket implements Serializable {
    private int place;
    private int row;
}
