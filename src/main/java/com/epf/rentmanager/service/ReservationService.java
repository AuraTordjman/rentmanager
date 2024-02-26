package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {

    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
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

    public List<Reservation> findByClientId(long clientId) throws ServiceException {
        try {
            return reservationDao.findByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des réservations par l'identifiant du client : " + e.getMessage(), e);
        }
    }

    public List<Reservation> findByVehicleId(long vehicleId) throws ServiceException {
        try {
            return reservationDao.findByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des réservations par l'identifiant du véhicule : " + e.getMessage(), e);
        }
    }

    public void delete(Reservation reservation) throws ServiceException {
        try {
            reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation : " + e.getMessage(), e);
        }
    }


}
