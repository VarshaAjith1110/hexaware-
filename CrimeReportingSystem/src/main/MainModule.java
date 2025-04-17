package main;

import dao.CrimeAnalysisServiceImpl;
import dao.ICrimeAnalysisService;
import entity.Incident;
import entity.Report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        ICrimeAnalysisService service = new CrimeAnalysisServiceImpl();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Crime Reporting System =====");
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. Search Incidents by Type");
            System.out.println("4. Search Incidents by Date Range");
            System.out.println("5. Generate Incident Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("--- Create New Incident ---");
                    System.out.print("Incident ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Type: ");
                    String type = sc.nextLine();
                    System.out.print("Location: ");
                    String location = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Status: ");
                    String status = sc.nextLine();
                    System.out.print("Victim ID: ");
                    int victimID = sc.nextInt();
                    System.out.print("Suspect ID: ");
                    int suspectID = sc.nextInt();
                    System.out.print("Officer ID: ");
                    int officerID = sc.nextInt();

                    Incident inc = new Incident(id, type, new Date(), location, desc, status, victimID, suspectID, officerID);
                    boolean created = service.createIncident(inc);
                    System.out.println(created ? " Incident created." : " Creation failed.");
                    break;

                case 2:
                    System.out.println("--- Update Incident Status ---");
                    System.out.print("Incident ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Status: ");
                    String newStatus = sc.nextLine();
                    boolean updated = service.updateIncidentStatus(updateId, newStatus);
                    System.out.println(updated ? " Status updated." : " Update failed.");
                    break;

                case 3:
                    System.out.println("--- Search by Type ---");
                    System.out.print("Enter incident type (e.g. Theft): ");
                    String searchType = sc.nextLine();
                    List<Incident> typeResults = service.searchIncidents(searchType);
                    if (typeResults.isEmpty()) {
                        System.out.println("⚠️ No incidents found for this type.");
                    } else {
                        for (Incident i : typeResults) {
                            System.out.println(i.getIncidentID() + " | " + i.getIncidentType() + " | " + i.getStatus());
                        }
                    }
                    break;

                case 4:
                    System.out.println("--- Search by Date Range ---");
                    try {
                        System.out.print("Start Date (yyyy-MM-dd): ");
                        String start = sc.nextLine();
                        System.out.print("End Date (yyyy-MM-dd): ");
                        String end = sc.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = sdf.parse(start);
                        Date endDate = sdf.parse(end);
                        List<Incident> dateResults = service.getIncidentsInDateRange(startDate, endDate);
                        if (dateResults.isEmpty()) {
                            System.out.println("⚠️ No incidents found in this date range.");
                        } else {
                            for (Incident i : dateResults) {
                                System.out.println(i.getIncidentID() + " | " + i.getIncidentDate() + " | " + i.getIncidentType());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;

                case 5:
                    System.out.println("--- Generate Report ---");
                    System.out.print("Enter Incident ID: ");
                    int reportId = sc.nextInt();
                    Report report = service.generateIncidentReport(reportId);
                    System.out.println("Report ID: " + report.getReportID());
                    System.out.println("Incident ID: " + report.getIncidentID());
                    System.out.println("Officer: " + report.getReportingOfficer());
                    System.out.println("Date: " + report.getReportDate());
                    System.out.println("Details: " + report.getReportDetails());
                    System.out.println("Status: " + report.getStatus());
                    break;

                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println(" Invalid option. Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
