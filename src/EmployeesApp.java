import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmployeesApp extends Application {
    private static int id=0;
    private Text  err = new Text(" ");
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employees Hierarchy");
        err.setFill(Color.RED);
        HBox root = new HBox();
        Scene scene = new Scene(root,-1,-1);
        VBox treeContainer = new VBox();
        VBox details = new VBox();
        VBox detail = new VBox();
        VBox add = new VBox();
        //Padding
        detail.setPadding(new Insets(10,20,30,20));
        add.setPadding(new Insets(10,20,10,20));

        //The CEO employee
        Employee CEO  = new CompositeEmployee(1,"CEO", 10000f,"All");
        TreeItem<Employee> CEO_item = new TreeItem<>(CEO);
        TreeView<Employee> treeView = new TreeView<>(CEO_item);
        treeView.getSelectionModel().select(0);

        //Details of selected employee
        Text addEmployee = new Text("Add Employee");
        Text name = new Text();
        Text salaryOwn = new Text();
        Text controlSpanCost = new Text("");


        Button computeSalary = new Button("Compute Salary");
        computeSalary.setOnAction(event -> {
            //Calculate the control span cost
            TreeItem<Employee> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Employee emp = selectedItem.getValue();
            name.setText("Name : " + emp.getEmployeeName());
            salaryOwn.setText("Salary : " + String.valueOf(emp.salary));
            controlSpanCost.setText(
                    "Control Span Cost : " + String.valueOf(Employee.depthFirstTraversal(emp)));
        });

        //Add the textfields and button
        detail.getChildren().add(name);
        detail.getChildren().add(salaryOwn);
        detail.getChildren().add(controlSpanCost);
        detail.getChildren().add(computeSalary);


        //Adding of an employee
        TextField newName = new TextField();
        TextField salary = new TextField();
        TextField dept = new TextField();
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton composite = new RadioButton("Composite Employee");
        RadioButton leaf = new RadioButton("Leaf Employee");
        composite.setToggleGroup(toggleGroup);
        leaf.setToggleGroup(toggleGroup);

        Button btn = new Button("Add");
        btn.setOnAction(event -> {
            TreeItem<Employee> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Employee employee = null;
            String newNameStr = newName.getText();
            String salaryStr = salary.getText();
            String dptStr = dept.getText();
            if( newNameStr.equals("") || salaryStr.equals("") || dptStr.equals("")){
                err.setText("Error All fields are required");
                return;
            }

            if (toggleGroup.getSelectedToggle() == null){
                err.setText("Error Type Not Set. All Fields Required");
                return;
            }

            String selected = ( (RadioButton)toggleGroup.getSelectedToggle()).getText();
            float salaryF;
            try{
                salaryF = Float.parseFloat(salaryStr);
            }
            catch(Exception e){
                err.setText("Error Salary Not Number.");
                return;
            }

            if(selected.equalsIgnoreCase("Composite Employee")){
                employee = new CompositeEmployee(id++, newNameStr,salaryF ,dptStr);
            }
            else if(selected.equalsIgnoreCase("leaf Employee")){
                employee = new LeafEmployee(id++, newNameStr, salaryF,dptStr);
            }
            addNewEmployee(selectedItem,employee);
        });
        newName.setPromptText("Name");
        salary.setPromptText("Salary");
        dept.setPromptText("Department");

        //Add them to the add container
        add.getChildren().add(addEmployee);
        add.getChildren().add(newName);
        add.getChildren().add(salary);
        add.getChildren().add(dept);
        add.getChildren().add(composite);
        add.getChildren().add(leaf);
        add.getChildren().add(btn);
        add.getChildren().add(err);

        //Add The detail and add containers
        details.getChildren().add(detail);
        details.getChildren().add(add);
        root.getChildren().add(treeContainer);
        root.getChildren().add(details);
        treeContainer.getChildren().add(treeView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addNewEmployee(TreeItem<Employee> selectedItem,Employee employee) {
        try {
            selectedItem.getValue().add(employee);
        } catch (NotValidLeafOperation notValidLeafOperation) {
            err.setText("Cant Add to a Leaf Employee");
            return;
        }
        selectedItem.getChildren().add(new TreeItem<>(employee));
        selectedItem.setExpanded(true);
        err.setText("");
    }
}
