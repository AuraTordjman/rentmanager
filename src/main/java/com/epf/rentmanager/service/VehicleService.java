package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}


	public long create(Vehicle vehicle) throws ServiceException {
		try {
			// Vérifier si le constructeur du véhicule est vide
			if (vehicle.getConstructeur().isEmpty()) {
				throw new ServiceException("Le constructeur du véhicule ne peut pas être vide.");
			}

			// Vérifier si le nombre de places du véhicule est supérieur à 1
			if (vehicle.getNb_places() <= 1) {
				throw new ServiceException("Le nombre de places du véhicule doit être supérieur à 1.");
			}

			// Appeler la méthode create du DAO pour insérer le véhicule dans la base de données
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			// En cas d'erreur lors de l'insertion dans la base de données, lever une ServiceException
			ServiceException serviceException = new ServiceException("Erreur lors de la création du véhicule : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}
	public void delete(long id) throws ServiceException {
		try {
			vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du véhicule : " + e.getMessage(), e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			// Appeler la méthode findById du DAO pour récupérer un véhicule par son id
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			// En cas d'erreur lors de la recherche dans la base de données, lever une ServiceException
			ServiceException serviceException = new ServiceException("Erreur lors de la recherche du véhicule par son identifiant : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			// Appeler la méthode findAll du DAO pour récupérer tous les véhicules
			return vehicleDao.findAll();
		} catch (DaoException e) {
			// En cas d'erreur lors de la recherche dans la base de données, lever une ServiceException
			ServiceException serviceException = new ServiceException("Erreur lors de la recherche de tous les véhicules : " + e.getMessage());
			serviceException.initCause(e);
			throw serviceException;
		}
	}
	
}
