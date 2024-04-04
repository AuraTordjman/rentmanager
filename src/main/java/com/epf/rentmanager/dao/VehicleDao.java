package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.DaoException;


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
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ? ,?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";

	public long create(Vehicle vehicule) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			ps.setString(1, vehicule.getConstructeur());
			ps.setString(2, vehicule.getModele());
			ps.setInt(3, vehicule.getNb_places());

			// Exécution de la requête
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();
			if(resultSet.next()){
				return resultSet.getInt(1);
			}else {
				System.out.println("Erreur lors de l'ajout du vehicule");
			}

		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule", e);
		}
		return -1;
	}


	public long delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			statement.setLong(1, id);

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La suppression du véhicule a échoué, aucune ligne affectée.");
			} else {
				return id; // Retourne l'identifiant du véhicule supprimé
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
	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM Vehicle");
			 ResultSet resultSet = statement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("La requête de comptage des véhicules n'a retourné aucun résultat.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des véhicules.", e);
		}
	}


}