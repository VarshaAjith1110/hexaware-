package dao;

import com.hexaware.myexceptions.IncidentNumberNotFoundException;
import entity.Incident;
import entity.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrimeAnalysisServiceImpl implements ICrimeAnalysisService {

    private List<Incident> incidentList = new ArrayList<>();
    private int reportCounter = 1;

    @Override
    public boolean createIncident(Incident incident) {
        return incidentList.add(incident);
    }

    @Override
    public boolean updateIncidentStatus(int incidentId, String newStatus) throws IncidentNumberNotFoundException {
        for (Incident incident : incidentList) {
            if (incident.getIncidentID() == incidentId) {
                incident.setStatus(newStatus);
                return true;
            }
        }
        throw new IncidentNumberNotFoundException("Incident with ID " + incidentId + " not found.");
    }

    @Override
    public List<Incident> searchIncidents(String type) {
        List<Incident> result = new ArrayList<>();
        for (Incident i : incidentList) {
            if (i.getIncidentType().equalsIgnoreCase(type)) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        List<Incident> result = new ArrayList<>();
        for (Incident i : incidentList) {
            if (!i.getIncidentDate().before(startDate) && !i.getIncidentDate().after(endDate)) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public Report generateIncidentReport(int incidentId) {
        for (Incident i : incidentList) {
            if (i.getIncidentID() == incidentId) {
                return new Report(reportCounter++, i.getIncidentID(), i.getOfficerID(), new Date(), i.getDescription(), i.getStatus());
            }
        }
        return null;
    }
}
