package account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import theatre.Person;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private String login;
    private String password;
    private Person person;
    private TypeOfAccount typeOfAccount;
}
