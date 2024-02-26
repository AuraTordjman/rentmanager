package com.epf.rentmanager.ui.cli;

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

            // Demandez les informations nécessaires pour créer une réservation
            // (par exemple : client, véhicule, dates, etc.)

            Reservation reservation = new Reservation();
            // Initialisez les attributs de la réservation

            long reservationId = reservationService.create(reservation);
            System.out.println("Réservation créée avec l'ID : " + reservationId);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    public void listReservations() {
        try {
            System.out.println("Liste des réservations :");

            for (Reservation reservation : reservationService.findAll()) {
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la récupération de la liste des réservations : " + e.getMessage());
        }
    }

    // Autres méthodes pour la suppression, etc.
}
