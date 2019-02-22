import java.util.ArrayList;

class LeafEmployee extends Employee{
    public LeafEmployee(int id,String name, float salary, String dept){
        this.name = name;
        this.salary = salary;
        this.dept = dept;
        this.id = id;
        //So that no NullPointerException gets thrown
        this.children = new ArrayList<>();

    }

    /*Add and Remove not supported in leaf employee
    * One way to handle this is by throwing an exception.
    **/
    @Override
    public void add(Employee e) throws NotValidLeafOperation {
        throw new NotValidLeafOperation();
    }

    @Override
    public void remove(Employee e) throws NotValidLeafOperation{
        throw new NotValidLeafOperation();
    }

}