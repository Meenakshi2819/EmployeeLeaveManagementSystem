package employee;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.util.Scanner;


public class EmployeeOperations {

	    Scanner sc = new Scanner(System.in);

	    // ADD EMPLOYEE
	    public void addEmployee() {

	        Connection con = DBConnection.getConnection();

	        try {

	            System.out.print("Enter Employee Name: ");
	            String name = sc.nextLine();

	            System.out.print("Enter Email: ");
	            String email = sc.nextLine();

	            System.out.print("Enter Department: ");
	            String department = sc.nextLine();

	            String sql = "INSERT INTO employees(name, email, department) VALUES (?, ?, ?)";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, department);

	            ps.executeUpdate();

	            System.out.println("Employee Registered Successfully!");

	            con.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // VIEW EMPLOYEES
	    public void viewEmployees() {

	        Connection con = DBConnection.getConnection();

	        try {

	            String fech = "SELECT * FROM employees";

	            PreparedStatement ps = con.prepareStatement(fech);

	            ResultSet rs = ps.executeQuery();

	            System.out.println("------ EMPLOYEE DETAILS -----");

	            while (rs.next()) {

	                System.out.println(rs.getInt("employee_id")+" | "+ rs.getString("name")+" | "+ rs.getString("email")+" | "+ rs.getString("department"));
	            }

	            con.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // DELETE EMPLOYEE //
	    public void deleteEmployee() {

	        Connection con = DBConnection.getConnection();

	        try {

	            System.out.print("Enter Employee ID to Delete: ");
	            int id = sc.nextInt();

	            String delete = "DELETE FROM employees WHERE employee_id=?";

	            PreparedStatement ps = con.prepareStatement(delete);

	            ps.setInt(1, id);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Employee Deleted Successfully!");
	            } 
	            else {
	                System.out.println("Employee Not Found!");
	            }

	            con.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}