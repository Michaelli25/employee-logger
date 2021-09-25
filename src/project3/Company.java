package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The Company class stores a grab bag container of employees and performs payroll operations.
 * The container automatically grows when it is filled. Company methods can add employees,
 * remove employees, set hours, and calculate payments.
 * @author Shane Hoffman, Michael Li
 */
public class Company {
    private Employee[] emplist;
    private int numEmployee;

    public static final int SIZE = 4;
    public static final int MISS = -1;

    /**
     * Creates a Company with an empty array of size 4 to store employees, setting
     * the number of employees to 0.
     */
    public Company() {
        this.emplist = new Employee[SIZE];
        this.numEmployee = 0;
    }

    /**
     * Gets the number of employees added to the emplist array.
     * @return - number of employees that have been added
     */
    public int getEmployees() {
        return this.numEmployee;
    }

    /**
     * Searches for a given employee inside the emplist array.
     * @param employee - the employee to search for in the emplist array
     * @return index - the index of the employee in the emplist array or -1 if it's a miss
     */
    private int find(Employee employee) {
        for(int i = 0; i < this.numEmployee; i++) {
            if(employee.equals(this.emplist[i]))
                return i;
        }

        return MISS;
    }

    /**
     * Helper method to grow the size of the emplist array when it's full.
     */
    private void grow() {
        Employee[] tempEmp = new Employee[this.numEmployee + SIZE];
        for(int i = 0; i < this.numEmployee; i++)
            tempEmp[i] = this.emplist[i];

        this.emplist = tempEmp;
    }

    /**
     * Adds an employee into the emplist array. If it's full then the helper method grow is called.
     * @param employee - the employee to add into the emplist array
     * @return true if the employee was successfully added, false otherwise
     */
    public boolean add(Employee employee) {
        if(numEmployee == this.emplist.length)
            this.grow();
        int index = this.find(employee);
        if(index != MISS)
            return false;
        for(int i = 0; i < this.emplist.length; i++) {
            if(this.emplist[i] == null) {
                this.emplist[i] = employee;
                this.numEmployee++;
                break;
            }
        }
        return true;
    }

    /**
     * Removes an employee from the emplist array. Shifts the array past the index to the
     * left by one position.
     * @param employee - the employee to remove from the emplist array
     * @return true if the employee was successfully removed, false otherwise
     */
    public boolean remove(Employee employee) {
        int index = this.find(employee);
        if(index == MISS)
            return false;
        for(int i = index; i < this.emplist.length-1; i++)
            this.emplist[i] = this.emplist[i+1];

        this.numEmployee--;
        return true;
    }

    /**
     * Sets the working hours for a part time employee.
     * @param employee - the employee to set their working hours for
     * @return true if the employees hours were successfully set, false otherwise
     */
    public boolean setHours(Employee employee) {     // Set working hours for a part-time
        int index = this.find(employee);
        if(index == MISS || employee instanceof Fulltime)
            return false;

        // call some part-time method to set hours
        Parttime temp = (Parttime) employee;
        Parttime real = (Parttime) this.emplist[index];

        real.setHours(temp.getHours());
        return true;
    }

    /**
     * Calculates the payments for every employee in the emplist array.
     */
    public void processPayments() {
        for(int i = 0; i < this.numEmployee; i++) {
            this.emplist[i].calculatePayment();
        }

    }

    /**
     * Prints out the list of employees in the emplist array in it's current order.
     */
    public String print() {
        String out = "";
        if(this.numEmployee == 0)
            out += "Employee database is empty.\n";
        else {
            out += "--Printing earning statements for all employees--\n";
            out += printer();
        }
        return out;
    }

    /**
     * Prints out the list of employees in the emplist array in alphabetical order by department.
     */
    public String printByDepartment() {
        String out = "";
        if(this.numEmployee == 0)
            out += "Employee database is empty.\n";
        else {
            insertionSort(false);
            out += "--Printing earning statements by department--\n";
            out += printer();
        }
        return out;
    }

    /**
     * Prints out the list of employees in the emplist array in ascending date hired order.
     */
    public String printByDate() {
        String out = "";
        if(this.numEmployee == 0)
            out += "Employee database is empty.\n";
        else {
            insertionSort(true);
            out += "--Printing earning statements by date hired--\n";
            out += printer();
        }
        return out;
    }

    /**
     * Helper method used by the print methods to sort the emplist array. It uses
     * insertion sort and will sort the emplist array by alphabetical department or
     * date hired in ascending order.
     * @param date - if true the method sorts by date hired, if false it sorts by
     *             alphabetical department
     */
    private void insertionSort(boolean date) {
        for(int i = 1; i < this.numEmployee; i++) {
            Employee k = this.emplist[i];
            int j = i - 1;

            if(date) {
                while (j >= 0 && this.emplist[j].getDate().compareTo(k.getDate()) > 0) {
                    this.emplist[j + 1] = this.emplist[j];
                    j = j - 1;
                }
            }
            else {
                while (j >= 0 && this.emplist[j].getDepartment().compareTo(k.getDepartment()) > 0) {
                    this.emplist[j + 1] = this.emplist[j];
                    j = j - 1;
                }
            }
            this.emplist[j + 1] = k;
        }
    }

    /**
     * Exports the emplist array to a text file passed to the method. Method utilizes
     * the company's printer() method, to convert the emplist to a readable string.
     * @param file - file we are exporting to
     * @return 1 if database successfully exported, 0 if a place wasn't selected
     * to save to
     */
    public int exportDatabase(File file) {
        if(file == null)
            return 0;
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        }
        catch(FileNotFoundException e) {
            return 0;
        }

        printWriter.append(printer());
        printWriter.close();
        return 1;
    }

    /**
     * Imports a text file into the company database. Parses the text file and adds
     * the correct employees to the emplist array.
     * @param file - file we are importing from
     * @return 1 if database successfully imported, 0 if a file wasn't selected to
     * import from
     */
    public int importDatabase(File file) {
        if(file == null)
            return 0;
        Scanner sc = null;
        try{
            sc = new Scanner(file);
        }
        catch(FileNotFoundException e) {
            return 0;
        }

        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(",");
            String[] dateFix = inputArr[3].split("/");
            Date date = new Date(dateFix[2] + "-" + dateFix[0] + "-" + dateFix[1]);
            Profile profile = new Profile(inputArr[1], inputArr[2], date);

            if(inputArr[0].equals("P")) {
                Double payRate = Double.parseDouble(inputArr[4]);
                this.add(new Parttime(profile, payRate));
            }
            else if(inputArr[0].equals("F")) {
                Double salary = Double.parseDouble(inputArr[4]);
                this.add(new Fulltime(profile, salary));
            }
            else if(inputArr[0].equals("M")) {
                Double salary = Double.parseDouble(inputArr[4]);
                int code = Integer.parseInt(inputArr[5]);
                this.add(new Management(profile, salary, code));
            }
        }

        return 1;
    }


    /**
     * Helper method for the print functions, print all employees in emplist array.
     */
    private String printer() {
        String append = "";
        for (int i = 0; i < this.numEmployee; i++)
            append += this.emplist[i].toString() + "\n";
        return append;
    }


}
