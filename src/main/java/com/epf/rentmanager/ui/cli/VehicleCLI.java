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

            System.out.print("Modèle : ");
            String modele = IOUtils.readString();

            System.out.print("Nombre de places : ");
            int nombreDePlaces = IOUtils.readInt("");

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(constructeur);
            vehicle.setModele(modele);
            vehicle.setNb_places(nombreDePlaces);
            long vehicleId = vehicleService.create(vehicle);
            System.out.println("Véhicule créé avec l'ID : " + vehicleId);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création du véhiculeCLI : " + e.getMessage());
        }
    }
    public void deleteVehicle() {
        try {
            listVehicles();
            long idToDelete = IOUtils.readInt("Entrez l'ID du véhicule à supprimer :");

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
    public static void main(String[] args) {
        VehicleService vehicleService = VehicleService.getInstance();
        VehicleCLI vehicleCLI = new VehicleCLI(vehicleService);

        boolean exit = false;

        do {
            displayMenu();
            int choice = 0;
            do {
                choice = IOUtils.readInt("Votre choix :");
            } while (choice < 1 || choice > 4); // Modifier le nombre d'options

            switch (choice) {
                case 1:
                    vehicleCLI.createVehicle();
                    break;
                case 2:
                    vehicleCLI.listVehicles();
                    break;
                case 3:
                    vehicleCLI.deleteVehicle();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option entre 1 et 4.");
            }
        } while (!exit);
    }

    private static void displayMenu() {
        System.out.println("Menu :");
        System.out.println("1. Créer un véhicule");
        System.out.println("2. Lister les véhicules");
        System.out.println("3. Supprimer un véhicule");
        System.out.println("4. Quitter");
    }
}
