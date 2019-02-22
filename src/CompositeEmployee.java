import java.util.ArrayList;

class  CompositeEmployee extends Employee{


    public CompositeEmployee(int id,String name, float salary, String dept){
        this.name = name;
        this.salary = salary;
        this.dept = dept;
        this.id = id;
        this.children = new ArrayList<>();
    }
    @Override
    public void add(Employee e) {
        children.add(e);
        e.parent = this;
    }

    @Override
    public void remove(Employee e) {
        for (int i = 0; i < children.size(); i++) {
            if(e.id == children.get(i).id){
                children.get(i).parent =null;
                children.remove(i);
            }
        }
    }
}