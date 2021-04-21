package theatre.classes;

import java.util.Objects;

public class Employee extends Person {

    private TypeOfEmployee typeOfEmployee;
    private float salary;
    private int workExperience;

    public Employee(String firstName, String lastName, int yearOfBirth,
                    TypeOfEmployee typeOfEmployee, float salary, int workExperience) {
        super(firstName, lastName, yearOfBirth);
        this.typeOfEmployee = typeOfEmployee;
        this.salary = salary;
        this.workExperience = workExperience;
    }

    public TypeOfEmployee getTypeOfEmployee() {
        return typeOfEmployee;
    }

    public void setTypeOfEmployee(TypeOfEmployee typeOfEmployee) {
        this.typeOfEmployee = typeOfEmployee;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Float.compare(employee.salary, salary) == 0
                && workExperience == employee.workExperience
                && typeOfEmployee == employee.typeOfEmployee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                typeOfEmployee, salary, workExperience);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "typeOfEmployee=" + typeOfEmployee +
                ", salary=" + salary +
                ", workExperience=" + workExperience +
                '}';
    }
}
