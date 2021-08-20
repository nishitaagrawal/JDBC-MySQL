package Lab8;
import java.sql.*;
import java.util.Scanner;

public class jdbcEmp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); try{
            // Connecting to Database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","","");
            if(con!=null){
                int ch;
                Statement st=con.createStatement();
                PreparedStatement pst;
                ResultSet rs;
                st.executeQuery("use employee");
                do{
                    System.out.println("\n****** EMPLOYEE DATABASE ******\n");
                    System.out.println("1. ADD RECORD\n2. DISPLAY RECORDS\n3. SEARCH A RECORD\n4. UPDATE A RECORD\n5. DELETE A RECORD\n6. EXIT");
                    System.out.print("\nEnter Your Choice: ");
                    ch = sc.nextInt();
                    switch(ch){
                        // Create
                        case 1: // Taking input
                            System.out.print("Enter Employee No: ");
                            int no = sc.nextInt();
                            System.out.print("Enter Employee Name: ");
                            String name = sc.next();
                            System.out.print("Enter Employee Department: ");
                            String dept = sc.next();
                            System.out.print("Enter Employee Job: ");
                            String job = sc.next();
                            System.out.print("Enter Employee Salary: ");
                            float salary = sc.nextFloat();
                            pst = con.prepareStatement("insert into Employee(empno, ename, department, job, salary) values (?, ?, ?, ?, ?)");
                            pst.setInt(1, no);
                            pst.setString(2, name);
                            pst.setString(3, dept);
                            pst.setString(4, job);
                            pst.setFloat(5, salary);
                            System.out.println("Record Added Successfully!");
                            pst.executeUpdate();
                            pst.close();
                            break;
                        case 2:// Read and Display records
                            st = con.createStatement();
                            rs = st.executeQuery("select * from Employee");
                            System.out.println("\n****** RECORDS *******");
                            System.out.println("------------------------------------------------------------------------------");
                            System.out.println("Emp_No \t\t Emp Name \t\t Emp Dept \t\t Emp Job \t\t Emp Salary");
                            System.out.println("------------------------------------------------------------------------------");
                            while(rs.next()){ System.out.print(rs.getInt("empno") + "\t\t\t");
                            System.out.print(rs.getString("ename") + "\t\t\t\t");
                            System.out.print(rs.getString("department") + "\t\t\t\t");
                            System.out.print(rs.getString("job") + "\t\t\t");
                            System.out.print(rs.getFloat("salary") + "\n"); }
                            st.close();
                            rs.close();
                            break;
                            // Search
                        case 3 : // Creating Input Name
                            System.out.print("Enter a Employee Name to find in database: ");
                            String name1 = sc.next(); // Creating Search Query
                            pst = con.prepareStatement("select * from Employee where ename=?");
                            pst.setString(1,name1); // Printing Record
                            rs = pst.executeQuery();
                            if(rs.next()){
                                System.out.println("****** RECORD FOUND *******");
                                System.out.println("Employee ID: " + rs.getInt("empno"));
                                System.out.println("Employee Name: " + rs.getString("ename"));
                                System.out.println("Employee Department: " + rs.getString("department"));
                                System.out.println("Employee Job: " + rs.getString("job"));
                                System.out.println("Employee Salary: "+ rs.getFloat("salary"));
                                System.out.println("***************************");
                            }
                            else{
                                System.out.println("No Records Found!");
                            }
                            rs.close();
                            pst.close();
                            break;
                            // Update
                        case 4: // Creating Input Name
                            System.out.print("Enter Employee ID to find in database: ");
                            int no1 = sc.nextInt(); System.out.print("Enter new salary to update record: ");
                            float salary1 = sc.nextFloat(); // Creating Update Query
                            pst = con.prepareStatement("update Employee set salary=? where empno=?;");
                            pst.setFloat(1,salary1);
                            pst.setInt(2, no1);
                            pst.executeUpdate();
                            System.out.println("Record Updated Successfully!");
                            pst.close();
                            break;
                            // Delete
                        case 5:
                            // Creating Input Name
                            System.out.print("Enter Employee ID to find in database: ");
                            int no2 = sc.nextInt();
                            // Creating Delete Query
                            pst = con.prepareStatement("delete from Employee where empno=?");
                            pst.setInt(1, no2);
                            System.out.println("Record Deleted Successfully");
                            pst.executeUpdate();
                            pst.close();
                            break;
                        case 6: System.exit(0);
                        default:
                            System.out.println("Enter a valid choice!");
                    }
                } while(true);
            }
            else{
                System.out.println("Connection Failed!");
            }
        }
        catch (Exception e){
            System.out.println("Error Found: " + e.getMessage());
        }
    }
}


