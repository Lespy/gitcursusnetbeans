package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Genre;
import be.vdab.entities.Voorstelling;

public class VoorstellingRepository extends AbstractRepository {
	private static final String SELECT_BY_ID = "select voorstellingen.id as voorstellingid,"
			+ " titel, uitvoerders, datum, genreid, genres.naam as genrenaam, prijs, vrijeplaatsen"
			+ " from voorstellingen inner join genres on genreid = genres.id" + " where voorstellingen.id = ?";
	private static final String FIND_PERFORMANCES_FROM_DATE_BY_GENRE = "select voorstellingen.id as voorstellingid, titel, uitvoerders, datum, genreid,"
			+ " genres.naam as genrenaam," + " prijs, vrijeplaatsen from voorstellingen inner join genres on"
			+ " genreid = genres.id where genreid = ?" + " and datum >= ? order by datum asc";

	public List<Voorstelling> findFuturePerformancesByGenreId(long id, LocalDateTime datumTijd) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_PERFORMANCES_FROM_DATE_BY_GENRE)) {
			List<Voorstelling> voorstellingen = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			statement.setTimestamp(2, Timestamp.valueOf(datumTijd));
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					voorstellingen.add(resultSetRijNaarVoorstelling(resultSet));
				}
			}
			connection.commit();
			return voorstellingen;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	public Optional<Voorstelling> read(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
			Optional<Voorstelling> voorstelling;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					voorstelling = Optional.of(resultSetRijNaarVoorstelling(resultSet));
				} else {
					voorstelling = Optional.empty();
				}
			}
			connection.commit();
			return voorstelling;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	private Voorstelling resultSetRijNaarVoorstelling(ResultSet resultSet) throws SQLException {
		return new Voorstelling(resultSet.getLong("voorstellingid"), resultSet.getString("titel"),
				resultSet.getString("uitvoerders"), resultSet.getTimestamp("datum"),
				new Genre(resultSet.getLong("genreid"), resultSet.getString("genrenaam")),
				resultSet.getBigDecimal("prijs"), resultSet.getInt("vrijePlaatsen"));
	}
}
