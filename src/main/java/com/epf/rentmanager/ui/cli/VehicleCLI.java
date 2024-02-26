package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

public class VehicleCLI {

    private VehicleService vehicleService;

    public VehicleCLI(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void createVehicle() {
        try {
            System.out.println("Création d'un nouveau véhicule :");

            System.out.print("Constructeur : ");
            String constructeur = IOUtils.readString();

            // Demander les autres informations nécessaires pour créer un véhicule

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(constructeur);
            // Set other attributes

            long vehicleId = vehicleService.create(vehicle);
            System.out.println("Véhicule créé avec l'ID : " + vehicleId);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    public void deleteVehicle() {
        try {
            long idToDelete = IOUtils.readInt("Entrez l'ID du véhicule à supprimer :");
            // Si IOUtils.readInt ne fonctionne pas, utilisez IOUtils.readLong() et convertissez le résultat en int si nécessaire

            vehicleService.delete(idToDelete);
            System.out.println("Véhicule supprimé avec succès.");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du véhicule : " + e.getMessage());
        }
    }

    public void listVehicles() {
        try {
            System.out.println("Liste des véhicules :");

            for (Vehicle vehicle : vehicleService.findAll()) {
                System.out.println(vehicle);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la récupération de la liste des véhicules : " + e.getMessage());
        }
    }
}
