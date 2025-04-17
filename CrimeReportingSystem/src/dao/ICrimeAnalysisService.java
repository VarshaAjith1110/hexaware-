// Interface: ICrimeAnalysisService.java

package dao;

import entity.Incident;
import entity.Report;

import java.util.Date;
import java.util.List;

public interface ICrimeAnalysisService {

    boolean createIncident(Incident incident);  // Method for creating an incident

    boolean updateIncidentStatus(int incidentId, String newStatus);  // Method for updating status

    List<Incident> getIncidentsInDateRange(Date startDate, Date endDate);  // Method to fetch incidents in a date range

    List<Incident> searchIncidents(String incidentType);  // Method to search incidents by type

    Report generateIncidentReport(int incidentId);  // Method for generating an incident report
}

