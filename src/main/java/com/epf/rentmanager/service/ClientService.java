package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientDao clientDao;
    private final ReservationService reservationService;


    public ClientService(ClientDao clientDao, ReservationService reservationService) {
        this.clientDao = clientDao;
        this.reservationService = reservationService;
    }


    public long create(Client client) throws ServiceException {
        try {
            // Vérification de l'âge du client
            LocalDate today = LocalDate.now();
            LocalDate ageMin = today.minusYears(18);
            if (client.getNaissance().isAfter(ageMin)) {
                throw new ServiceException("Le client doit avoir au moins 18 ans.");
            }
            // Vérifier si le nom ou le prénom du client est vide
            if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
                throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
            }
            // Vérifier si le nom et le prénom du client ont au moins 3 caractères
            if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
                throw new ServiceException("Le nom et le prénom du client doivent contenir au moins 3 caractères.");
            }
            // Vérifier si l'adresse e-mail du client est déjà utilisée
            if (clientDao.findByEmail(client.getEmail()) != null) {
                throw new ServiceException("L'adresse e-mail est déjà utilisée par un autre client.");
            }

            // Convertir le nom de famille en majuscules
            client.setNom(client.getNom().toUpperCase());

            // Appeler la méthode create du DAO pour insérer le client dans la base de données
            long clientId = clientDao.create(client);

            // Vérifier si l'opération de création a réussi
            if (clientId == 0) {
                throw new ServiceException("Erreur lors de la création du client.");
            }

            return clientId;
        } catch (DaoException e) {
            // En cas d'erreur lors de l'insertion dans la base de données, lever une ServiceException
            throw new ServiceException("Erreur lors de la création du client : " + e.getMessage());
        }
    }
    public void delete(long id) throws ServiceException {
        try {
            // Récupérer les réservations associées au client
            List<Reservation> reservations = reservationService.findByClient(id);

            // Supprimer les réservations associées
            for (Reservation reservation : reservations) {
                reservationService.delete(reservation.getId());
            }

            // Supprimer le client après avoir supprimé toutes les réservations associées
            clientDao.delete(id);
        } catch (ServiceException | DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du client et de ses réservations associées", e);
        }
    }



    public Client findById(long id) throws ServiceException {
        try {
            // Appeler la méthode findById du DAO pour récupérer un client par son id
            return clientDao.findById(id);
        } catch (DaoException e) {
            // En cas d'erreur lors de la recherche dans la base de données, lever une ServiceException
            ServiceException serviceException = new ServiceException("Erreur lors de la recherche du client par son identifiant : " + e.getMessage());
            serviceException.initCause(e);
            throw serviceException;
        }
    }

    public List<Client> findAll() throws ServiceException {
        try {
            // Appeler la méthode findAll du DAO pour récupérer tous les clients
            return clientDao.findAll();
        } catch (DaoException e) {
            // En cas d'erreur lors de la recherche dans la base de données, lever une ServiceException
            ServiceException serviceException = new ServiceException("Erreur lors de la recherche de tous les clients : " + e.getMessage());
            serviceException.initCause(e);
            throw serviceException;
        }
    }
    public int countClients() throws ServiceException, DaoException {
        return clientDao.count();
    }
    public void update(Client client) throws ServiceException {
        try {
            // Vérification de l'âge du client
            LocalDate today = LocalDate.now();
            LocalDate ageMin = today.minusYears(18);
            if (client.getNaissance().isAfter(ageMin)) {
                throw new ServiceException("Le client doit avoir au moins 18 ans.");
            }
            // Vérifier si le nom ou le prénom du client est vide
            if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
                throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
            }
            // Vérifier si le nom et le prénom du client ont au moins 3 caractères
            if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
                throw new ServiceException("Le nom et le prénom du client doivent contenir au moins 3 caractères.");
            }
            // Vérifier si l'adresse e-mail du client est déjà utilisée
            Client existingClient = clientDao.findByEmail(client.getEmail());
            if (existingClient != null && existingClient.getId() != client.getId()) {
                throw new ServiceException("L'adresse e-mail est déjà utilisée par un autre client.");
            }

            // Convertir le nom de famille en majuscules
            client.setNom(client.getNom().toUpperCase());

            // Appeler la méthode update du DAO pour mettre à jour le client dans la base de données
            clientDao.update(client);
        } catch (DaoException e) {
            // En cas d'erreur lors de la mise à jour dans la base de données, lever une ServiceException
            throw new ServiceException("Erreur lors de la mise à jour du client : " + e.getMessage(), e);
        }
    }

}
