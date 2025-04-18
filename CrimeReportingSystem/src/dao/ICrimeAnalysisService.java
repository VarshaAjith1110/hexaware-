package dao;

import com.hexaware.myexceptions.IncidentNumberNotFoundException;
import entity.Incident;
import entity.Report;

import java.util.Date;
import java.util.List;

public interface ICrimeAnalysisService {
    boolean createIncident(Incident incident);
    
    boolean updateIncidentStatus(int incidentId, String newStatus) throws IncidentNumberNotFoundException;
    
    List<Incident> searchIncidents(String type);
    List<Incident> getIncidentsInDateRange(Date startDate, Date endDate);
    Report generateIncidentReport(int incidentId);
}
