package account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String firstname;
    private String lastname;
    private String phone;
    private Account account;

    public String getLogin() {
        return account.getLogin();
    }

    public String getPassword() {
        return account.getPassword();
    }

    public void setLogin(String login){
        this.account.setLogin(login);
    }

    public void setPassword(String password){
        this.account.setPassword(password);
    }
}
