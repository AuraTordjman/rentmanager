package com.epf.rentmanager.ui.cli;

import java.time.LocalDate;
import java.util.List;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

public class ReservationCLI {

    private ReservationService reservationService;

    public ReservationCLI(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void createReservation() {
        try {
            System.out.println("Création d'une nouvelle réservation :");

            System.out.print("Identifiant du client : ");
            int clientId = IOUtils.readInt("");

            System.out.print("Identifiant du véhicule : ");
            int vehicleId = IOUtils.readInt("");

            System.out.print("Date de début (AAAA-MM-JJ) : ");
            LocalDate debut = LocalDate.parse(IOUtils.readString());

            System.out.print("Date de fin (AAAA-MM-JJ) : ");
            LocalDate fin = LocalDate.parse(IOUtils.readString());

            Reservation reservation = new Reservation();
            reservation.setClient_id(clientId);
            reservation.setVehicle_id(vehicleId);
            reservation.setDebut(debut);
            reservation.setFin(fin);

            long reservationId = reservationService.create(reservation);
            System.out.println("Réservation créée avec l'ID : " + reservationId);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }
    public void deleteReservation() {
        try {
            System.out.println("Suppression d'une réservation :");
            long reservationId = IOUtils.readInt("Entrez l'ID de la réservation à supprimer :");
            reservationService.delete(reservationId);
            System.out.println("Réservation supprimée avec succès.");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }


    public void listReservations() {
        try {
            System.out.println("Liste des réservations :");
            List<Reservation> reservations = reservationService.findAll();
            System.out.println("Nombre de réservations : " + reservations.size());

            for (Reservation reservation : reservations) {
                IOUtils.print(reservation.toString());
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la récupération de la liste des réservations : " + e.getMessage());
        }
    }
    /*
    public static void main(String[] args) {
        boolean exit = false;
        ReservationService reservationService = ReservationService.getInstance();
        ReservationCLI reservationCLI = new ReservationCLI(reservationService);

        do {
            displayMenu();
            int choice = 0;
            do {
                choice = IOUtils.readInt("Votre choix :");
            } while (choice < 1 || choice > 4); // Modifier le nombre d'options

            switch (choice) {
                case 1:
                    reservationCLI.createReservation();
                    break;
                case 2:
                    reservationCLI.listReservations();
                    break;
                case 3:
                    reservationCLI.deleteReservation();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option entre 1 et 3."); // Modifier le message d'erreur
            }
        } while (!exit);
    }

    private static void displayMenu() {
        System.out.println("Menu :");
        System.out.println("1. Créer une réservation");
        System.out.println("2. Lister les réservations");
        System.out.println("3. Supprimer une réservation");
        System.out.println("4. Quitter");
    }
*/
}
