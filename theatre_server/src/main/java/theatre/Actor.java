package theatre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Actor extends Person implements Serializable {

    private TypeOfActor typeOfActor;

    @Builder
    public Actor(String name, String surname, TypeOfActor typeOfActor) {
        super(name, surname);
        this.typeOfActor = typeOfActor;
    }


}
