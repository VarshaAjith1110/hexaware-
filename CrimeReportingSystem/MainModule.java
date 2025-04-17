package main;

import entity.Incident;
import dao.ICrimeAnalysisService;

import dao.CrimeAnalysisServiceImpl;

import java.util.Date;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        ICrimeAnalysisService service = new CrimeAnalysisServiceImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Incident Creation ===");

        System.out.print("Enter Incident ID: ");
        int incidentID = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Incident Type (e.g., Theft, Assault): ");
        String type = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Status (Open/Closed): ");
        String status = scanner.nextLine();

        System.out.print("Enter Victim ID: ");
        int victimID = scanner.nextInt();

        System.out.print("Enter Suspect ID: ");
        int suspectID = scanner.nextInt();

        System.out.print("Enter Officer ID: ");
        int officerID = scanner.nextInt();
        scanner.close();

        // Creating incident with current date
        Incident newIncident = new Incident(
                incidentID,
                type,
                new Date(), // incident date is current time
                location,
                description,
                status,
                victimID,
                suspectID,
                officerID
        );

        boolean isCreated = service.createIncident(newIncident);
        System.out.println("Incident creation successful: " + isCreated);
    }
}
