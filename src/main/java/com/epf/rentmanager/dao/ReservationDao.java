package com.epf.rentmanager.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;

import java.time.LocalDate;


import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {
	public ReservationDao() {}
	/*private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	 */

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	public long create(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			ps.setInt(1,reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

			// Exécution de la requête
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();
			if(resultSet.next()){
				return resultSet.getInt(1);
			}

		}catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
		return -1;
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
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);){

			// Assignation de la valeur au paramètre de la requête
			ps.setInt(1, (int) clientId);

			//execution de la requete
			ps.execute();
			//resultat de la requete
			ResultSet resultSet = ps.executeQuery();
			List<Reservation>ReservatClientX = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, (int) clientId, vehicle_id, debut, fin);
				ReservatClientX.add(reservation);
			}
			return ReservatClientX;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservation par ID client", e);
		}
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
					int Newid = (int) id;
					int client_id = resultSet.getInt("client_id");
					int vehicle_id = resultSet.getInt("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					Reservation reservation = new Reservation(Newid, client_id, vehicle_id, debut, fin);
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
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";

	public void update(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_RESERVATION_QUERY)) {
			statement.setLong(1, reservation.getClient_id());
			statement.setLong(2, reservation.getVehicle_id());
			statement.setDate(3, java.sql.Date.valueOf(reservation.getDebut()));
			statement.setDate(4, java.sql.Date.valueOf(reservation.getFin()));
			statement.setLong(5, reservation.getId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La mise à jour de la resa a échoué, aucune ligne affectée.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la mise à jour de la resa.", e);
		}
	}
	public List<Reservation> findByVehicleAndDates(long vehicleId, LocalDate begin, LocalDate end) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		String query = "SELECT * FROM Reservation WHERE vehicle_id = ? AND ((debut >= ? AND debut <= ?) OR (fin >= ? AND fin <= ?))";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, vehicleId);
			statement.setDate(2, Date.valueOf(begin));
			statement.setDate(3, Date.valueOf(end));
			statement.setDate(4, Date.valueOf(begin));
			statement.setDate(5, Date.valueOf(end));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(resultSet.getInt("id"));
					reservation.setClient_id(resultSet.getInt("client_id"));
					reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
					reservation.setDebut(resultSet.getDate("debut").toLocalDate());
					reservation.setFin(resultSet.getDate("fin").toLocalDate());
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des réservations pour un véhicule donné et une période donnée : " + e.getMessage(), e);
		}
		return reservations;
	}

	public boolean isVehicleReservedForThirtyDaysOrMore(long vehicleId) throws DaoException {
		final int MIN_CONSECUTIVE_DAYS = 30;
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT debut, fin FROM Reservation WHERE vehicle_id = ? ORDER BY debut ASC")) {
			statement.setLong(1, vehicleId);

			try (ResultSet resultSet = statement.executeQuery()) {
				LocalDate lastEndDate = null;
				int consecutiveDaysReserved = 0;

				while (resultSet.next()) {
					LocalDate currentStartDate = resultSet.getDate("debut").toLocalDate();
					LocalDate currentEndDate = resultSet.getDate("fin").toLocalDate();

					if (lastEndDate != null && lastEndDate.plusDays(1).isEqual(currentStartDate)) {
						// Il n'y a pas de pause entre les réservations
						consecutiveDaysReserved += currentEndDate.compareTo(currentStartDate) + 1;
					} else {
						// Il y a une pause entre les réservations
						consecutiveDaysReserved = currentEndDate.compareTo(currentStartDate) + 1;
					}

					lastEndDate = currentEndDate;

					if (consecutiveDaysReserved >= MIN_CONSECUTIVE_DAYS) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la vérification de la durée de la réservation du véhicule", e);
		}
		return false;
	}

}
