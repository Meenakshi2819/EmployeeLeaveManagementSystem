
package employee;

import java.util.Scanner;

public class EmployeeLeaveManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DBConnection.showConnectionMessage();

        EmployeeOperations employee = new EmployeeOperations();
        LeaveOperations leave = new LeaveOperations();
        LeaveBalanceOperations balance = new LeaveBalanceOperations();

        while (true) {

            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT SYSTEM =====");
            System.out.println("1. Employee Registration");
            System.out.println("2. Apply Leave");
            System.out.println("3. Approve / Reject Leave");
            System.out.println("4. View Leave Balance");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    // Employee Registration//
                    employee.addEmployee();

                    // Create leave balance for employee//
                    System.out.print("Enter Employee ID to create leave balance: ");
                    int employeeId = sc.nextInt();

                    balance.createBalance(employeeId);
                    break;

                case 2:
                    // Apply Leave//
                    leave.applyLeave();
                    break;

                case 3:
                    // Approve / Reject Leave//

                    System.out.println("\n----- LEAVE REQUESTS -----");
                    leave.viewLeaveRequests();

                    System.out.println("\n1. Approve Leave");
                    System.out.println("2. Reject Leave");

                    System.out.print("Enter choice: ");
                    int action = sc.nextInt();

                    if (action == 1) {
                        leave.approveLeave();
                    } 
                    else if (action == 2) {
                        leave.rejectLeave();
                    } 
                    else {
                        System.out.println("Invalid Choice!");
                    }

                    break;

                case 4:
                    // View Leave Balance//
                    balance.viewBalance();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice! Please enter 1 to 5.");
            }
        }
    }
}


