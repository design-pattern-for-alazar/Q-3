import java.util.ArrayList;

/*Defines a uniform interface for all employee types
 * Also provides some default implementations.
 */
abstract class Employee{

    /*Used to traverse and calculate the controlSpanCost of
     *the sub tree which has
     *a composite employee as the root.
     */
    public static float depthFirstTraversal(Employee root){
        root.controlSpanCost = root.getSalary();
        for (Employee emp :
                root.getMemberEmployees()) {
            root.controlSpanCost += depthFirstTraversal(emp);
        }
        return root.controlSpanCost;
    }

    public String getEmployeeName(){
        return this.name;
    }
    public float getSalary(){
        return this.salary;
    }
    public ArrayList<Employee> getMemberEmployees() {
        return this.children;
    }
    @Override
    public String toString(){
        return this.name;
    }

    abstract public void add(Employee e) throws NotValidLeafOperation;
    abstract public void remove(Employee e) throws NotValidLeafOperation;


    protected Employee parent = null;
    protected String name;
    protected float salary;
    protected float controlSpanCost;
    protected String dept;
    protected int id;
    protected ArrayList<Employee> children;
}

class NotValidLeafOperation extends Exception{
    public NotValidLeafOperation(){
        super("Method Not supported on leaf component");
    }
}