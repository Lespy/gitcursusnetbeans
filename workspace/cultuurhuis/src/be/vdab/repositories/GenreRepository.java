package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Genre;

public class GenreRepository extends AbstractRepository {
	private static final String FIND_ALL = "select id, naam from genres order by naam asc";
	private static final String FIND_BY_ID = "select id, naam from genres where id = ?";
	public List<Genre> findAll() {
		try(Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {
			List<Genre> genres = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			try(ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
				while(resultSet.next()) {
					genres.add(resultSetRijNaarGenre(resultSet));
				}
			}
			connection.commit();
			return genres;
		} catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	public Optional<Genre> findById(long id) {
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
			Optional<Genre> genre;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					genre = Optional.of(resultSetRijNaarGenre(resultSet));
				}else {
					genre = Optional.empty();
				}
			}
			connection.commit();
			return genre;
		} catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	private Genre resultSetRijNaarGenre(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	}
}
