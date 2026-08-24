/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalward;

/**
 *
 * @author Student
 */
import java.util.ArrayList;

public class WardManager {
    private final ArrayList<Patient> patientList;
    private final String[][] beds; // 4 rows x 5 columns (20 beds)

    public WardManager() {
        patientList = new ArrayList<>();
        beds = new String[4][5];
        initBeds();
    }

    private void initBeds() {
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (count < 10) {
                    beds[i][j] = "B0" + count;
                } else {
                    beds[i][j] = "B" + count;
                }
                count++;
            }
        }
    }

    // CRUD - Add / Register
    public boolean registerPatient(Patient patient) {
        if (findPatient(patient.getPatientID()) != null) {
            System.out.println("Error: Patient ID " + patient.getPatientID() + " already exists!");
            return false;
        }
        patientList.add(patient);
        return true;
    }

    // CRUD - Search
    public Patient findPatient(String patientID) {
        for (Patient p : patientList) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    // CRUD - Delete
    public boolean deletePatient(String patientID) {
        Patient p = findPatient(patientID);
        if (p != null) {
            if (p instanceof Inpatient inp) {
                releaseBed(inp.getBedNumber());
            }
            patientList.remove(p);
            return true;
        }
        return false;
    }

    // Bed Management
    public boolean allocateBed(Inpatient patient, String bedCode) {
        if (patient.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Only INPATIENT category can be assigned a bed.");
            return false;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (beds[i][j].equalsIgnoreCase(bedCode)) {
                    beds[i][j] = "[OCCUPIED]";
                    patient.setBedNumber(bedCode);
                    return true;
                }
            }
        }
        System.out.println("Bed " + bedCode + " is unavailable or does not exist.");
        return false;
    }

    public boolean releaseBed(String bedCode) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (beds[i][j].equalsIgnoreCase("[OCCUPIED]")) {
                    beds[i][j] = bedCode;
                    return true;
                }
            }
        }
        return false;
    }

    public void displayBeds() {
        System.out.println("\n========== WARD 2D BED GRID (4x5) ==========");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(beds[i][j] + "\t\t");
            }
            System.out.println();
        }
        System.out.println("============================================");
    }

    // Report Logic
    public int getOccupiedCount() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (beds[i][j].equals("[OCCUPIED]")) {
                    count++;
                }
            }
        }
        return count;
    }

    public double calculateOccupancyPercentage() {
        return (getOccupiedCount() / 20.0) * 100.0;
    }

    public void printOccupancyReport() {
        int occupied = getOccupiedCount();
        int available = 20 - occupied;
        double rate = calculateOccupancyPercentage();

        System.out.println("\n========== WARD OCCUPANCY REPORT ==========");
        System.out.println("Total Registered Patients: " + patientList.size());
        System.out.println("Total Beds: 20");
        System.out.println("Occupied Beds: " + occupied);
        System.out.println("Available Beds: " + available);
        System.out.println("Occupancy Rate: " + rate + "%");
        System.out.println("===========================================");
    }

    // Bubble Sort by Last Name (Lecture-friendly algorithm)
    public void sortByLastName() {
        for (int i = 0; i < patientList.size() - 1; i++) {
            for (int j = 0; j < patientList.size() - i - 1; j++) {
                String name1 = patientList.get(j).getLastName();
                String name2 = patientList.get(j + 1).getLastName();
                if (name1.compareToIgnoreCase(name2) > 0) {
                    Patient temp = patientList.get(j);
                    patientList.set(j, patientList.get(j + 1));
                    patientList.set(j + 1, temp);
                }
            }
        }
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public String[][] getBeds() {
        return beds;
    }
}