package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dao.CrimeAnalysisServiceImpl;
import dao.ICrimeAnalysisService;
import entity.Incident;
import com.hexaware.myexceptions.IncidentNumberNotFoundException;

import java.util.Date;

public class CrimeAnalysisServiceImplTest {

    ICrimeAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new CrimeAnalysisServiceImpl();
    }

    @Test
    void testCreateIncident() {
        Incident i = new Incident(1, "Robbery", new Date(), "City Center", "Cash stolen", "Open", 1, 2, 3);
        boolean result = service.createIncident(i);
        assertTrue(result);
    }

    @Test
    void testUpdateIncidentStatus() throws IncidentNumberNotFoundException {
        Incident i = new Incident(2, "Theft", new Date(), "Mall", "Phone theft", "Open", 4, 5, 6);
        service.createIncident(i);
        boolean updated = service.updateIncidentStatus(2, "Closed");
        assertTrue(updated);
    }

    @Test
    void testUpdateIncidentStatus_NotFound() {
        Exception ex = assertThrows(IncidentNumberNotFoundException.class, () -> {
            service.updateIncidentStatus(999, "Closed");
        });
        assertEquals("Incident with ID 999 not found.", ex.getMessage());
    }
}
