/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalward;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class HospitalWardTest {
    private WardManager manager;

    @BeforeEach
    public void setUp() {
        manager = new WardManager();
    }

    @Test
    public void testRegisterPatient() {
        Inpatient p = new Inpatient("P101", "John", "Doe", 30, "Male", "Flu", 1, "B01");
        assertTrue(manager.registerPatient(p));
        assertNotNull(manager.findPatient("P101"));
    }

    @Test
    public void testPreventDuplicateID() {
        Inpatient p1 = new Inpatient("P101", "John", "Doe", 30, "Male", "Flu", 1, "B01");
        Inpatient p2 = new Inpatient("P101", "Jane", "Smith", 25, "Female", "Fever", 1, "B02");
        
        manager.registerPatient(p1);
        assertFalse(manager.registerPatient(p2));
    }

    @Test
    public void testAllocateBedAndOccupancy() {
        Inpatient p1 = new Inpatient("P101", "John", "Doe", 30, "Male", "Flu", 1, "B01");
        manager.registerPatient(p1);
        
        assertTrue(manager.allocateBed(p1, "B01"));
        assertEquals(1, manager.getOccupiedCount());
        assertEquals(5.0, manager.calculateOccupancyPercentage(), 0.01);
    }

    @Test
    public void testDeletePatientFreesBed() {
        Inpatient p1 = new Inpatient("P101", "John", "Doe", 30, "Male", "Flu", 1, "B01");
        manager.registerPatient(p1);
        manager.allocateBed(p1, "B01");

        assertTrue(manager.deletePatient("P101"));
        assertNull(manager.findPatient("P101"));
    }
}