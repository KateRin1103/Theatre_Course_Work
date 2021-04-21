package account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import theatre.Spectacle;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Account account;
    private Spectacle spectacle;
    private List<Ticket> tickets;
    private Date dateOfOrder;
}
