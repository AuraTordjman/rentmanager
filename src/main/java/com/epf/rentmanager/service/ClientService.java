package com.epf.rentmanager.service;
import java.time.LocalDate;
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

            LocalDate today = LocalDate.now();
            LocalDate ageMin = today.minusYears(18);
            if (client.getNaissance().isAfter(ageMin)) {
                throw new ServiceException("Le client doit avoir au moins 18 ans.");
            }

            if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
                throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
            }

            if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
                throw new ServiceException("Le nom et le prénom du client doivent contenir au moins 3 caractères.");
            }

            if (clientDao.findByEmail(client.getEmail()) != null) {
                throw new ServiceException("L'adresse e-mail est déjà utilisée par un autre client.");
            }


            client.setNom(client.getNom().toUpperCase());


            long clientId = clientDao.create(client);


            if (clientId == 0) {
                throw new ServiceException("Erreur lors de la création du client.");
            }

            return clientId;
        } catch (DaoException e) {

            throw new ServiceException("Erreur lors de la création du client : " + e.getMessage());
        }
    }
    public void delete(long id) throws ServiceException {
        try {

            List<Reservation> reservations = reservationService.findByClient(id);


            for (Reservation reservation : reservations) {
                reservationService.delete(reservation.getId());
            }


            clientDao.delete(id);
        } catch (ServiceException | DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du client et de ses réservations associées", e);
        }
    }



    public Client findById(long id) throws ServiceException {
        try {

            return clientDao.findById(id);
        } catch (DaoException e) {

            ServiceException serviceException = new ServiceException("Erreur lors de la recherche du client par son identifiant : " + e.getMessage());
            serviceException.initCause(e);
            throw serviceException;
        }
    }

    public List<Client> findAll() throws ServiceException {
        try {

            return clientDao.findAll();
        } catch (DaoException e) {

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

            LocalDate today = LocalDate.now();
            LocalDate ageMin = today.minusYears(18);
            if (client.getNaissance().isAfter(ageMin)) {
                throw new ServiceException("Le client doit avoir au moins 18 ans.");
            }

            if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
                throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
            }

            if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
                throw new ServiceException("Le nom et le prénom du client doivent contenir au moins 3 caractères.");
            }

            Client existingClient = clientDao.findByEmail(client.getEmail());
            if (existingClient != null && existingClient.getId() != client.getId()) {
                throw new ServiceException("L'adresse e-mail est déjà utilisée par un autre client.");
            }


            client.setNom(client.getNom().toUpperCase());


            clientDao.update(client);
        } catch (DaoException e) {

            throw new ServiceException("Erreur lors de la mise à jour du client : " + e.getMessage(), e);
        }
    }

}
