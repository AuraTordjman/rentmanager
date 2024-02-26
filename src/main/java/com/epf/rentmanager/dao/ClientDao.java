package com.epf.rentmanager.dao;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.epf.rentmanager.persistence.ConnectionManager;
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS))
		{
			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4, java.sql.Date.valueOf(client.getNaissance()));

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DaoException("La création du client a échoué, aucune ligne affectée.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					return generatedKeys.getLong(1); // Retourne l'identifiant généré pour le client créé
				} else
				{
					throw new DaoException("La création du client a échoué, aucun identifiant retourné.");
				}
			}
		} catch (SQLException e)
		{
			throw new DaoException("Erreur lors de la création du client.", e);
		}
	}

	public long delete(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			statement.setLong(1, clientId);

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La suppression du client a échoué, aucune ligne affectée.");
			} else {
				return clientId; // Retourne l'identifiant du client supprimé
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client.", e);
		}
	}



	public Client findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_CLIENT_QUERY)) {
			statement.setLong(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String nom = resultSet.getString("nom");
					String prenom = resultSet.getString("prenom");
					String email = resultSet.getString("email");
					LocalDate naissance = resultSet.getDate("naissance").toLocalDate();

					return new Client(id, nom, prenom, email, naissance);
				} else {
					throw new DaoException("Aucun client trouvé avec l'identifiant : " + id);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par identifiant.", e);
		}
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();

				Client client = new Client(id, nom, prenom, email, naissance);
				clients.add(client);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les clients.", e);
		}
		return clients;
	}


}
