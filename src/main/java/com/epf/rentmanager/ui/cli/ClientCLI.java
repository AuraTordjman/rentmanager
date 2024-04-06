package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

public class ClientCLI {

    private ClientService clientService;

    public ClientCLI(ClientService clientService) {
        this.clientService = clientService;
    }

    public void createClient() {
        try {
            System.out.println("Création d'un nouveau client :");

            System.out.print("Nom : ");
            String nom = IOUtils.readString();

            System.out.print("Prénom : ");
            String prenom = IOUtils.readString();

            System.out.print("Email : ");
            String email = IOUtils.readString();

            System.out.print("Date de naissance (yyyy-MM-dd) : ");
            LocalDate naissance = LocalDate.parse(IOUtils.readString());

            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(naissance);

            long clientId = clientService.create(client);
            System.out.println("Client créé avec l'ID : " + clientId);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    public void listClients() {
        try {
            System.out.println("Liste des clients :");

            for (Client client : clientService.findAll()) {
                System.out.println(client);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la récupération de la liste des clients : " + e.getMessage());
        }
    }
    public void deleteClient() {
        try {
            System.out.println("Suppression d'un client :");
            listClients(); // Afficher la liste des clients disponibles pour lui rappeler ceux qui existent

            // Demande de saisir l'id du client à supprimer
            long idToDelete = IOUtils.readInt("Entrez l'ID du client à supprimer : ");

            // Appeler la méthode de suppression du ClientService
            clientService.delete(idToDelete);
            System.out.println("Client supprimé avec succès !");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

/*
    public static void main(String[] args) {
        boolean exit = false;
        ClientService clientService = ClientService.getInstance();
        ClientCLI clientCLI = new ClientCLI(clientService);

        do {
            displayMenu();
            int choice = 0;
            do {
                choice = IOUtils.readInt("Votre choix :");
            } while (choice < 1 || choice > 4); // Modifier le nombre d'options

            switch (choice) {
                case 1:
                    clientCLI.createClient();
                    break;
                case 2:
                    clientCLI.listClients();
                    break;
                case 3:
                    clientCLI.deleteClient();
                    break; // Ajouter un case pour la suppression
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option entre 1 et 4."); // Modifier le message d'erreur
            }
        } while (!exit);
    }

    private static void displayMenu() {
        System.out.println("Menu :");
        System.out.println("1. Créer un client");
        System.out.println("2. Lister les clients");
        System.out.println("3. Supprimer un client"); // Ajouter une option de suppression
        System.out.println("4. Quitter");
    }

 */
}
