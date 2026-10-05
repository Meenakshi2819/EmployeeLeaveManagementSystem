package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LeaveOperations {

    Scanner sc = new Scanner(System.in);

    // APPLY LEAVE//
    public void applyLeave() {

        Connection con = DBConnection.getConnection();

        try {

            System.out.print("Enter Employee ID: ");
            int employeeId = sc.nextInt();

            System.out.print("Enter Number of Leave Days: ");
            int days = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter Reason: ");
            String reason = sc.nextLine();

            // CHECK BALANCE //
            String balanceSQL ="SELECT remaining_leaves FROM leave_balance WHERE employee_id=?";

            PreparedStatement balancePS = con.prepareStatement(balanceSQL);

            balancePS.setInt(1, employeeId);

            ResultSet rs = balancePS.executeQuery();

            if (rs.next()) {

                int remainingLeaves = rs.getInt("remaining_leaves");

                if (days <= remainingLeaves) {

                    String insert = "INSERT INTO leave_requests(employee_id, leave_days, reason, status) VALUES (?, ?, ?, 'PENDING')";

                    PreparedStatement ps =con.prepareStatement(insert);
                    ps.setInt(1, employeeId);
                    ps.setInt(2, days);
                    ps.setString(3, reason);

                    ps.executeUpdate();

                    System.out.println("Leave Applied Successfully!");

                } 
                else {

                    System.out.println( "Insufficient Leave Balance!");
                }

            } 
            else {

                System.out.println("Employee Leave Balance Not Found!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW LEAVE REQUESTS
    public void viewLeaveRequests() {

        Connection con = DBConnection.getConnection();

        try {

            String fech = "SELECT * FROM leave_requests";

            PreparedStatement ps =con.prepareStatement(fech);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- LEAVE REQUESTS -----");

            while (rs.next()) {

                System.out.println( "Leave ID: " + rs.getInt("leave_id")
                    + " | Employee ID: " + rs.getInt("employee_id")
                    + " | Days: " + rs.getInt("leave_days")
                    + " | Reason: " + rs.getString("reason")
                    + " | Status: " + rs.getString("status"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // APPROVE LEAVE
    public void approveLeave() {

        Connection con = DBConnection.getConnection();

        try {

            System.out.print("Enter Leave ID to Approve: ");
            int leaveId = sc.nextInt();

            // START TRANSACTION
            con.setAutoCommit(false);

            // GET LEAVE DETAILS
            String selectSQL = "SELECT employee_id, leave_days FROM leave_requests WHERE leave_id=? AND status='PENDING'";

            PreparedStatement selectPS =
                    con.prepareStatement(selectSQL);

            selectPS.setInt(1, leaveId);

            ResultSet rs = selectPS.executeQuery();

            if (rs.next()) {

                int employeeId =
                        rs.getInt("employee_id");

                int days =
                        rs.getInt("leave_days");

                // UPDATE LEAVE STATUS
                String updateLeave ="UPDATE leave_requests SET status='APPROVED' WHERE leave_id=?";

                PreparedStatement leavePS =con.prepareStatement(updateLeave);
                leavePS.setInt(1, leaveId);

                leavePS.executeUpdate();

                // UPDATE LEAVE BALANCE
                String updateBalance = "UPDATE leave_balance SET used_leaves = used_leaves + ?, remaining_leaves = remaining_leaves - ? WHERE employee_id=?";

                PreparedStatement balancePS =con.prepareStatement(updateBalance);

                balancePS.setInt(1, days);
                balancePS.setInt(2, days);
                balancePS.setInt(3, employeeId);

                balancePS.executeUpdate();

                // COMMIT
                con.commit();

                System.out.println("Leave Approved Successfully!");

            } 
            else {

                System.out.println( "Leave Request Not Found or Already Processed!" );

                con.rollback();
            }

            con.setAutoCommit(true);

            con.close();

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    // REJECT LEAVE
    public void rejectLeave() {

        Connection con = DBConnection.getConnection();

        try {

            System.out.print("Enter Leave ID to Reject: ");
            int leaveId = sc.nextInt();

            String sql ="UPDATE leave_requests SET status='REJECTED' WHERE leave_id=? AND status='PENDING'";

            PreparedStatement ps =  con.prepareStatement(sql);

            ps.setInt(1, leaveId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println( "Leave Rejected Successfully!"  );

            } 
            else {

                System.out.println("Leave Request Not Found or Already Processed!" );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}