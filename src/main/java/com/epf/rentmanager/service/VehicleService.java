package com.epf.rentmanager.service;
import java.util.List;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
	private final ReservationService reservationService;

	public VehicleService(VehicleDao vehicleDao, ReservationService reservationService) {
		this.vehicleDao = vehicleDao;
		this.reservationService = reservationService;
	}



	public long create(Vehicle vehicle) throws ServiceException {
		try {

			if (vehicle.getConstructeur().isEmpty()) {
				throw new ServiceException("Le constructeur du véhicule ne peut pas être vide.");
			}
			if (vehicle.getModele().isEmpty()) {
				throw new ServiceException("Le modèle du véhicule ne peut pas être vide.");
			}

			if (vehicle.getNb_places() <= 2) {
				throw new ServiceException("Le nombre de places du véhicule doit être supérieur à 2.");
			}
			if (vehicle.getNb_places() > 9) {
				throw new ServiceException("Le nombre de places du véhicule doit être inférieur à 9.");
			}

			return vehicleDao.create(vehicle);
		} catch (DaoException e) {

			ServiceException serviceException = new ServiceException("Erreur lors de la création du véhicule : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}
	public void delete(long id) throws ServiceException {
		try {

			List<Reservation> reservations = reservationService.findByVehicle(id);


			for (Reservation reservation : reservations) {
				reservationService.delete(reservation.getId());
			}


			vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du véhicule : " + e.getMessage(), e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {

			return vehicleDao.findById(id);
		} catch (DaoException e) {

			ServiceException serviceException = new ServiceException("Erreur lors de la recherche du véhicule par son identifiant : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {

			return vehicleDao.findAll();
		} catch (DaoException e) {

			ServiceException serviceException = new ServiceException("Erreur lors de la recherche de tous les véhicules : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}
	public int countVehicles() throws DaoException {
		return vehicleDao.count();
	}
	public void update(Vehicle vehicle) throws ServiceException {
		try {
			vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Error updating client: " + e.getMessage(), e);
		}
	}


}
