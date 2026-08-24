/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hospitalward;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class HospitalWard {

    public static void main(String[] args) {
        WardManager manager = new WardManager();
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            
            while (running) {
                System.out.println("\n===== HOSPITAL WARD MANAGEMENT SYSTEM =====");
                System.out.println("1. Register Inpatient & Allocate Bed");
                System.out.println("2. Display 2D Bed Grid");
                System.out.println("3. Search Patient by ID");
                System.out.println("4. View All Patients (Sorted by Last Name)");
                System.out.println("5. View Occupancy Report");
                System.out.println("6. Delete Patient Record");
                System.out.println("7. Exit");
                System.out.print("Enter choice (1-7): ");
                
                String choice = sc.nextLine();
                
                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter Patient ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter First Name: ");
                        String fname = sc.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lname = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();
                        System.out.print("Enter Medical Condition: ");
                        String cond = sc.nextLine();
                        System.out.print("Enter Ward Number: ");
                        int ward = Integer.parseInt(sc.nextLine());
                        
                        manager.displayBeds();
                        System.out.print("Enter Bed Code to allocate (e.g. B01): ");
                        String bedCode = sc.nextLine().toUpperCase();
                        
                        Inpatient p = new Inpatient(id, fname, lname, age, gender, cond, ward, bedCode);
                        if (manager.registerPatient(p)) {
                            if (manager.allocateBed(p, bedCode)) {
                                System.out.println("Patient registered and bed " + bedCode + " assigned successfully.");
                            }
                        }
                    }
                        
                    case "2" -> manager.displayBeds();
                        
                    case "3" -> {
                        System.out.print("Enter Patient ID to search: ");
                        String searchId = sc.nextLine();
                        Patient found = manager.findPatient(searchId);
                        if (found != null) {
                            System.out.println("\nPatient Found:");
                            found.display();
                        } else {
                            System.out.println("No patient found with ID " + searchId);
                        }
                    }
                        
                    case "4" -> {
                        manager.sortByLastName();
                        System.out.println("\n========== PATIENTS SORTED BY LAST NAME ==========");
                        for (Patient patient : manager.getPatientList()) {
                            patient.display();
                        }
                    }
                        
                    case "5" -> manager.printOccupancyReport();
                        
                    case "6" -> {
                        System.out.print("Enter Patient ID to delete: ");
                        String delId = sc.nextLine();
                        if (manager.deletePatient(delId)) {
                            System.out.println("Patient deleted and bed released.");
                        } else {
                            System.out.println("Patient ID not found.");
                        }
                    }
                        
                    case "7" -> {
                        running = false;
                        System.out.println("Exiting System. Goodbye!");
                    }
                        
                    default -> System.out.println("Invalid option. Please enter 1-7.");
                }
            }
        }
    }
}
