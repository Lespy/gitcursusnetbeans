package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
	private static final String CREATE = "insert into reservaties("
			+ "klantid, voorstellingsid, plaatsen) values(?,?,?)";
	private static final String UPDATE_VRIJE_PLAATSEN = "update voorstellingen"
			+ " set vrijeplaatsen = vrijeplaatsen - ?"
			+ " where id = ? and vrijeplaatsen >= ?";
	public boolean create(Reservatie reservatie) {
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statementUpdatePlaatsen = connection.prepareStatement(
						UPDATE_VRIJE_PLAATSEN);
				PreparedStatement statementCreateReservatie = connection.prepareStatement(
						CREATE, Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statementUpdatePlaatsen.setInt(1, reservatie.getAantalPlaatsen());
			statementUpdatePlaatsen.setLong(2, reservatie.getVoorstelling().getId());
			statementUpdatePlaatsen.setInt(3, reservatie.getAantalPlaatsen());
			if (statementUpdatePlaatsen.executeUpdate() != 0) {
				statementCreateReservatie.setLong(1, reservatie.getKlant().getId());
				statementCreateReservatie.setLong(2, reservatie.getVoorstelling().getId());
				statementCreateReservatie.setInt(3, reservatie.getAantalPlaatsen());
				statementCreateReservatie.executeUpdate();
				try(ResultSet resultSet = statementCreateReservatie.getGeneratedKeys()) {
					resultSet.next();
					reservatie.setId(resultSet.getLong(1));
				}
				connection.commit();
				return true;
			}else {
				return false;
			}
		} catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
