package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import be.vdab.entities.Adres;
import be.vdab.entities.Klant;

public class KlantRepository extends AbstractRepository {
	private static final String FIND_BY_USERNAME = "select id, voornaam, familienaam,"
			+ " straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord"
			+ " from klanten where gebruikersnaam = ?";
	
	private static final String CREATE = "insert into klanten(voornaam, familienaam, straat,"
			+ " huisnr, postcode, gemeente, gebruikersnaam, paswoord) values (?,?,?,?,?,?,?,?)";

	public Optional<Klant> read(String gebruikersnaam) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME)) {
			Optional<Klant> klant;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, gebruikersnaam);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlant(resultSet));
				} else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	public void create(Klant klant) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, klant.getVoornaam());
			statement.setString(2, klant.getFamilienaam());
			statement.setString(3, klant.getAdres().getStraat());
			statement.setInt(4, klant.getAdres().getHuisNr());
			statement.setInt(5, klant.getAdres().getPostcode());
			statement.setString(6, klant.getAdres().getGemeente());
			statement.setString(7, klant.getGebruikersnaam());
			statement.setString(8, klant.getPaswoord());
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				klant.setId(resultSet.getLong(1));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	private Klant resultSetRijNaarKlant(ResultSet resultSet) throws SQLException {
		return new Klant(resultSet.getLong("id"), resultSet.getString("voornaam"), resultSet.getString("familienaam"),
				new Adres(resultSet.getString("straat"), resultSet.getInt("huisnr"), resultSet.getInt("postcode"),
						resultSet.getString("gemeente")),
				resultSet.getString("gebruikersnaam"), resultSet.getString("paswoord"));
	}
}
