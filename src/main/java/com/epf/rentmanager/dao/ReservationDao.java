package com.epf.rentmanager.dao;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;

import java.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.epf.rentmanager.persistence.ConnectionManager;
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
			try (Connection connection = ConnectionManager.getConnection();
				 PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY, PreparedStatement.RETURN_GENERATED_KEYS))
			{
				statement.setLong(1, reservation.getClient_id());
				statement.setLong(2, reservation.getVehicle_id());
				statement.setDate(3, java.sql.Date.valueOf(reservation.getDebut()));
				statement.setDate(4, java.sql.Date.valueOf(reservation.getFin()));

				int affectedRows = statement.executeUpdate();
				if (affectedRows == 0)
				{
					throw new DaoException("La création de la réservation a échoué, aucune ligne affectée.");
				}

				try (ResultSet generatedKeys = statement.getGeneratedKeys())
				{
					if (generatedKeys.next())
					{
						return generatedKeys.getLong(1); // Retourne l'identifiant généré pour la réservation créée
					} else
					{
						throw new DaoException("La création de la réservation a échoué, aucun identifiant retourné.");
					}
				}
			} catch (SQLException e)
			{
				throw new DaoException("Erreur lors de la création de la réservation.", e);
			}
		}


	public long delete(Reservation reservation) throws DaoException {
		String query = "DELETE FROM reservation WHERE id = ?";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, reservation.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Error while deleting reservation: " + e.getMessage(), e);
		}
	}
	public long deleteParID(long reservationId) throws DaoException {
		String query = "DELETE FROM reservation WHERE id = ?";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, reservationId);
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation : " + e.getMessage(), e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		return new ArrayList<Reservation>();
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		return new ArrayList<Reservation>();
	}
	public List<Reservation> findByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservation WHERE vehicle_id = ?")) {
			statement.setLong(1, vehicleId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId((int) resultSet.getLong("id"));
					// Récupérer d'autres colonnes de la table et définir les attributs de la réservation
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error while finding reservations by vehicle id: " + e.getMessage(), e);
		}
		return reservations;
	}
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				int client_id = resultSet.getInt("client_id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();

				Reservation reservation = new Reservation((int) id, client_id, vehicle_id, debut, fin);
				reservations.add(reservation);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de toutes les réservations.", e);
		}
		return reservations;
	}
	public Reservation findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservation WHERE id = ?")) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId((int) resultSet.getLong("id"));
					// Set other attributes of reservation
					return reservation;
				} else {
					throw new DaoException("Réservation non trouvée avec l'identifiant : " + id);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la réservation par son identifiant : " + e.getMessage(), e);
		}
	}

	public List<Reservation> findByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		String query = "SELECT * FROM reservation WHERE client_id = ?";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, clientId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId((int) resultSet.getLong("id"));
					reservation.setClient_id((int) resultSet.getLong("client_id"));
					reservation.setVehicle_id((int) resultSet.getLong("vehicle_id"));
					reservation.setDebut(resultSet.getDate("start_date").toLocalDate());
					reservation.setFin(resultSet.getDate("end_date").toLocalDate());
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error while finding reservations by client ID: " + e.getMessage(), e);
		}
		return reservations;
	}
	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM Reservation");
			 ResultSet resultSet = statement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("La requête de comptage des réservations n'a retourné aucun résultat.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des réservations.", e);
		}
	}


}
