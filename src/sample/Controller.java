package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import project3.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The Controller class takes the users inputs from the GUI using JavaFX.
 * The class holds a Company object. The actions you can perform on the GUI are:
 * add, remove, and set the hours of an employee. You can also print out the database
 * on the second tab. Import and export options are also available.
 * @author Shane Hoffman, Michael Li
 */
public class Controller implements Initializable{
    private Company company;

    private static final int MAX_HOURS = 100;
    private static final int MANAGER_CODE = 1;
    private static final int DEPARTMENT_HEAD_CODE = 2;
    private static final int DIRECTOR_CODE = 3;
    private static final int EARLIEST_YEAR = 1900;

    @FXML
    private TextField name;
    @FXML
    private RadioButton csBtn;
    @FXML
    private RadioButton eceBtn;
    @FXML
    private DatePicker datePick;
    @FXML
    private RadioButton fullBtn;
    @FXML
    private RadioButton manageBtn;
    @FXML
    private RadioButton partBtn;
    @FXML
    private RadioButton manager;
    @FXML
    private RadioButton departmentHead;
    @FXML
    private RadioButton director;
    @FXML
    private Button setBtn;
    @FXML
    private TextField salary;
    @FXML
    private TextField hours;
    @FXML
    private TextField rate;
    @FXML
    private TextArea tabOne;
    @FXML
    private TextArea tabTwo;

    /**
     * Helper method that disables and enables inputs relative to a
     * manager employee.
     * @param b - boolean that determines if inputs are going to be
     *          enabled or disabled
     */
    private void changeManage(boolean b) {
        manager.setDisable(b);
        departmentHead.setDisable(b);
        director.setDisable(b);
    }

    /**
     * Helper method that disables and enables inputs relative to a
     * part time employee.
     * @param b - boolean that determines if inputs are going to be
     *          enabled or disabled
     */
    private void changePart(boolean b) {
        salary.setDisable(!b);
        hours.setDisable(b);
        rate.setDisable(b);
        setBtn.setDisable(b);
    }

    /**
     * Based on which employee is selected, it will disable certain
     * inputs from the user.
     * @param event
     */
    @FXML
    void display(ActionEvent event) {
        if(fullBtn.isSelected()) {
            changeManage(true);
            changePart(true);
        }
        else if(manageBtn.isSelected()) {
            changeManage(false);
            changePart(true);
        }
        else if(partBtn.isSelected()){
            changeManage(true);
            changePart(false);
        }
    }

    /**
     * Helper method that checks if basic inputs like name,
     * department, and date have been filled out or not.
     * @return 1 if inputs are filled, 0 if an input is empty
     */
    private int checkInputs() {
        if(name.getText() == null || name.getText().isEmpty()) {
            tabOne.appendText("Name field is empty.\n");
            return 0;
        }
        else if(datePick.getValue() == null) {
            tabOne.appendText("Date field is empty.\n");
            return 0;
        }
        return 1;
    }

    /**
     * Helper method to grab the department selected.
     * @return departmentString - the department selected
     */
    private String grabDepartment() {
        if(csBtn.isSelected())
            return "CS";
        else if(eceBtn.isSelected())
            return "ECE";
        else
            return "IT";
    }

    /**
     * Adds a full time employee to the Company. Checks if salary
     * is properly formatted and if the employee is already in the list.
     */
    private void addFull() {
        if(salary.getText().equals("")) {
            tabOne.appendText("Salary is empty.\n");
            return;
        }
        Double annualSalary;
        try {
            annualSalary = Double.parseDouble(salary.getText());
        } catch (NumberFormatException e) {
            tabOne.appendText("Input for Salary is incorrect. Use an integer or a real number.\n");
            return;
        }

        if (annualSalary < 0)
            tabOne.appendText("Salary cannot be negative.\n");
        else {
            Date date = new Date(datePick.getValue().toString());
            Profile profile = new Profile(name.getText(), grabDepartment(), date);
            Fulltime fulltime = new Fulltime(profile, annualSalary);
            if (this.company.add(fulltime))
                tabOne.appendText("Employee added.\n");
            else
                tabOne.appendText("Employee is already in the list.\n");
        }
    }

    /**
     * Adds a managerial employee to the Company. Checks if salary
     * is properly formatted and if the employee is already in the list.
     */
    private void addManage() {
        if(salary.getText().equals("")) {
            tabOne.appendText("Salary is empty.\n");
            return;
        }
        Double annualSalary;
        try {
            annualSalary = Double.parseDouble(salary.getText());
        } catch (NumberFormatException e) {
            tabOne.appendText("Input for Salary is incorrect. Use an integer or a real number.\n");
            return;
        }

        if(annualSalary < 0)
            tabOne.appendText("Salary cannot be negative.\n");
        else {
            Date date = new Date(datePick.getValue().toString());
            Profile profile = new Profile(name.getText(), grabDepartment(), date);
            int code = MANAGER_CODE;
            if(departmentHead.isSelected())
                code = DEPARTMENT_HEAD_CODE;
            else if(director.isSelected())
                code = DIRECTOR_CODE;
            Management management = new Management(profile, annualSalary, code);
            if (this.company.add(management))
                tabOne.appendText("Employee added.\n");
            else
                tabOne.appendText("Employee is already in the list.\n");
        }
    }

    /**
     * Adds a part time employee to the Company. Checks if pay rate
     * is properly formatted and if an employee is already in the list.
     */
    private void addPart() {
        if(rate.getText().equals("")) {
            tabOne.appendText("Hourly rate is empty.\n");
            return;
        }
        Double payRate;
        try {
            payRate = Double.parseDouble(rate.getText());
        }
        catch(NumberFormatException e) {
            tabOne.appendText("Input for Hourly Rate is incorrect. Use an integer or a real number.\n");
            return;
        }

        if(payRate < 0) {
            tabOne.appendText("Hourly Rate cannot be negative.\n");
        }
        else {
            Date date = new Date(datePick.getValue().toString());
            Profile profile = new Profile(name.getText(), grabDepartment(), date);
            Parttime parttime = new Parttime(profile, payRate);
            if(this.company.add(parttime))
                tabOne.appendText("Employee added.\n");
            else
                tabOne.appendText("Employee is already in the list.\n");
        }
    }

    /**
     * When the add button is clicked in the GUI this method
     * determines which type of employee to add.
     * @param event
     */
    @FXML
    void add(ActionEvent event) {
        if(checkInputs() == 1) {
            if(fullBtn.isSelected()) {
                addFull();
            }
            else if(manageBtn.isSelected()) {
                addManage();
            }
            if(partBtn.isSelected()) {
                addPart();
            }
        }
    }

    /**
     * When the remove button is clicked in the GUI this method
     * determines which type of employee to remove. It checks
     * if the database is empty or if the employee exists.
     * @param event
     */
    @FXML
    void remove(ActionEvent event) {
        if(this.company.getEmployees() == 0) {
            tabOne.appendText("Employee database is empty.\n");
            return;
        }
        if(checkInputs() == 1) {
            Date date = new Date(datePick.getValue().toString());
            Profile profile = new Profile(name.getText(), grabDepartment(), date);
            Employee employee = new Employee(profile);
            if(this.company.remove(employee))
                tabOne.appendText("Employee removed.\n");
            else
                tabOne.appendText("Employee does not exist.\n");
        }
    }

    /**
     * When the set hours button is clicked in the GUI this method
     * sets the hours inputted to the proper employee. It checks
     * if the database is empty, period hours is properly formatted
     * or if the employee exists.
     * @param event
     */
    @FXML
    void setHours(ActionEvent event) {
        if(this.company.getEmployees() == 0) {
            tabOne.appendText("Employee database is empty.\n");
            return;
        }
        if(hours.getText().equals("")) {
            tabOne.appendText("Hours Worked is empty.\n");
            return;
        }
        int periodHours;
        try {
            periodHours = Integer.parseInt(hours.getText());
        }
        catch(NumberFormatException e) {
            tabOne.appendText("Input for Hours is incorrect. Use an integer.\n");
            return;
        }

        if(periodHours < 0)
            tabOne.appendText("Working Hours cannot be negative.\n");
        else if(periodHours > MAX_HOURS)
            tabOne.appendText("Invalid Hours: over 100.\n");
        else {
            Date date = new Date(datePick.getValue().toString());
            Profile profile = new Profile(name.getText(), grabDepartment(), date);
            Parttime parttime = new Parttime(profile, periodHours);
            if(this.company.setHours(parttime))
                tabOne.appendText("Working hours set.\n");
            else
                tabOne.appendText("Employee does not exist.\n");
        }
    }

    /**
     * Quality of life method that clears all input text fields
     * when the clear fields button is clicked.
     * @param event
     */
    @FXML
    void clear(ActionEvent event) {
        name.clear();
        datePick.setValue(null);
        salary.clear();
        hours.clear();
        rate.clear();
    }

    /**
     * When the print button is clicked it will print
     * the Company database to the second tabs text area.
     * @param event
     */
    @FXML
    void print(ActionEvent event) {
        tabTwo.appendText(this.company.print());
    }

    /**
     * When the print by date button is clicked it will print
     * the Company database in date hired order to the second
     * tabs text area.
     * @param event
     */
    @FXML
    void printDate(ActionEvent event) {
        tabTwo.appendText(this.company.printByDate());
    }

    /**
     * When the print by department button is clicked it will print
     * the Company database in alphabetical department order to the
     * second tabs text area.
     * @param event
     */
    @FXML
    void printDepartment(ActionEvent event) {
        tabTwo.appendText(this.company.printByDepartment());
    }

    /**
     * Calculates all the employees payments inside the Company
     * when the calculate button is clicked.
     * @param event
     */
    @FXML
    void calculate(ActionEvent event) {
        if(this.company.getEmployees() == 0) {
            tabTwo.appendText("Employee database is empty.\n");
            return;
        }
        this.company.processPayments();
        tabTwo.appendText("Calculation of employee payments is done.\n");
    }

    /**
     * When the export button is clicked this method will open a file
     * chooser allowing the user to select a place to export.
     * Then it will take the contents of the Company database similar
     * to the print method and save it to a text file by calling the
     * exportDatabase() method.
     * @param event
     */
    @FXML
    void export(ActionEvent event) {
        if(this.company.getEmployees() == 0) {
            tabTwo.appendText("Employee database is empty.\n");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(ext);
        fileChooser.setTitle("Save Database");
        File file = fileChooser.showSaveDialog(null);
        if(this.company.exportDatabase(file) != 0)
            tabTwo.appendText("Database successfully exported to: " + file.getName() + "\n");
        else
            tabTwo.appendText("File not selected, please try again.\n");
    }

    /**
     * When the import button is clicked this method will open a file
     * chooser allowing the user to select a text file to import.
     * Then it will parse the file and add the appropriate employees
     * by calling the importDatabase() method.
     * @param event
     */
    @FXML
    void importer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(ext);
        fileChooser.setTitle("Select a File to Import");
        File file = fileChooser.showOpenDialog(null);
        if(this.company.importDatabase(file) != 0)
            tabTwo.appendText("Database successfully imported.\n");
        else
            tabTwo.appendText("File not selected, please try again.\n");
    }

    /**
     * This method is to setup the GUI on startup. It disables invalid
     * dates from the date picker and sets the values of the department
     * choice box. It also initializes the Company object.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePick.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(LocalDate.now()) ||
                                        item.isBefore(LocalDate.of(EARLIEST_YEAR, 1, 1))) {
                                    setDisable(true);
                                }
                            }
                        };
                    }
                };
        datePick.setDayCellFactory(dayCellFactory);
        datePick.setValue(null);
        this.company = new Company();
    }
}
