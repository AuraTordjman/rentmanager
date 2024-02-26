package com.epf.rentmanager.dao;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	

	public long create(Vehicle vehicle) throws DaoException
	{
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS))
		{
			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNb_places());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DaoException("La création du véhicule a échoué, aucune ligne affectée.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					return generatedKeys.getLong(1); // Retourne l'identifiant généré pour le véhicule créé
				}
				else
				{
					throw new DaoException("La création du véhicule a échoué, aucun identifiant retourné.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DaoException("Erreur lors de la création du véhicule.", e);
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			statement.setLong(1, vehicle.getId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La suppression du véhicule a échoué, aucune ligne affectée.");
			} else {
				return vehicle.getId(); // Retourne l'identifiant du véhicule supprimé
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule.", e);
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {
			statement.setLong(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String constructeur = resultSet.getString("constructeur");
					String modele = resultSet.getString("modele");
					int nb_places = resultSet.getInt("nb_places");

					return new Vehicle(id, constructeur, modele, nb_places);
				} else {
					throw new DaoException("Aucun véhicule trouvé avec l'identifiant : " + id);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule par identifiant.", e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_places = resultSet.getInt("nb_places");

				Vehicle vehicle = new Vehicle(id, constructeur, modele, nb_places);
				vehicles.add(vehicle);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les véhicules.", e);
		}
		return vehicles;
	}


}