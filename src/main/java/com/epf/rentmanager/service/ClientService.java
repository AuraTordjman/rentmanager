package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

    private ClientDao clientDao;
    private static ClientService instance;

    private ClientService() {
        this.clientDao = ClientDao.getInstance();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public long create(Client client) throws ServiceException {
        try {
            // Vérifier si le nom ou le prénom du client est vide
            if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
                throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
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
}
