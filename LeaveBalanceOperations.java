package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LeaveBalanceOperations {

    Scanner sc = new Scanner(System.in);

    // CREATE LEAVE BALANCE
    public void createBalance(int employeeId) {

        Connection con = DBConnection.getConnection();

        try {

            String insert = "INSERT INTO leave_balance(employee_id, total_leaves, used_leaves, remaining_leaves) VALUES (?, 20, 0, 20)";

            PreparedStatement ps = con.prepareStatement(insert);

            ps.setInt(1, employeeId);

            ps.executeUpdate();

            System.out.println("Leave Balance Created!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW LEAVE BALANCE
    public void viewBalance() {

        Connection con = DBConnection.getConnection();

        try {

            System.out.print("Enter Employee ID: ");
            int employeeId = sc.nextInt();

            String sql = "SELECT * FROM leave_balance WHERE employee_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("----- LEAVE BALANCE -----");

                System.out.println( rs.getInt("employee_id")+" | "+ rs.getInt("total_leaves")+" | "+rs.getInt("used_leaves")+" | "+rs.getInt("remaining_leaves"));

            } 
            else {

                System.out.println("Leave Balance Not Found!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}