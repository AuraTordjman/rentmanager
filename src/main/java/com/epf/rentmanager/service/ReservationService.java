package com.epf.rentmanager.service;

import java.util.List;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;


    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException {
        try {
            if (reservation.getDebut().isAfter(reservation.getFin())) {
                throw new ServiceException("La date de début ne peut pas être postérieure à la date de fin.");
            }
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création de la réservation : " + e.getMessage(), e);
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de la réservation par son identifiant : " + e.getMessage(), e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de toutes les réservations : " + e.getMessage(), e);
        }
    }

    public void deletebyObject(Reservation reservation) throws ServiceException {
        try {
            reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation : " + e.getMessage(), e);
        }
    }
    public void delete(long reservationId) throws ServiceException {
        try {
            reservationDao.deleteParID(reservationId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation : " + e.getMessage(), e);
        }
    }
    public int countReservations() throws ServiceException, DaoException {
        return reservationDao.count();
    }
    public List<Reservation> findByClient(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id du client (reservation)", e);
        }
    }
    public void update(Reservation reservation) throws ServiceException {
        try {
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Error updating reservation: " + e.getMessage(), e);
        }
    }

}
