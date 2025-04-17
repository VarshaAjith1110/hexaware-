// Service Implementation: CrimeAnalysisServiceImpl.java

package dao;

import entity.Incident;
import entity.Report;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrimeAnalysisServiceImpl implements ICrimeAnalysisService {

    private Connection conn;

    public CrimeAnalysisServiceImpl() {
        conn = DBConnUtil.getConnection();
    }

    @Override
    public boolean createIncident(Incident incident) {
        String sql = "INSERT INTO Incidents VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, incident.getIncidentID());
            stmt.setString(2, incident.getIncidentType());
            stmt.setDate(3, new java.sql.Date(incident.getIncidentDate().getTime()));
            stmt.setString(4, incident.getLocation());
            stmt.setString(5, incident.getDescription());
            stmt.setString(6, incident.getStatus());
            stmt.setInt(7, incident.getVictimID());
            stmt.setInt(8, incident.getSuspectID());
            stmt.setInt(9, incident.getOfficerID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIncidentStatus(int incidentId, String newStatus) {
        String sql = "UPDATE Incidents SET status = ? WHERE incidentID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, incidentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT * FROM Incidents WHERE incidentDate BETWEEN ? AND ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Incident inc = new Incident(
                    rs.getInt("incidentID"),
                    rs.getString("incidentType"),
                    rs.getDate("incidentDate"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getInt("victimID"),
                    rs.getInt("suspectID"),
                    rs.getInt("officerID")
                );
                list.add(inc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Incident> searchIncidents(String incidentType) {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT * FROM Incidents WHERE incidentType = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, incidentType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Incident inc = new Incident(
                    rs.getInt("incidentID"),
                    rs.getString("incidentType"),
                    rs.getDate("incidentDate"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getInt("victimID"),
                    rs.getInt("suspectID"),
                    rs.getInt("officerID")
                );
                list.add(inc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Report generateIncidentReport(int incidentId) {
        // Stub method - for now just return a dummy report
        return new Report(1, incidentId, 1, new Date(), "Details about incident #" + incidentId, "Draft");
    }
}
