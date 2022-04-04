package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Jogos;
import model.Times;

public class JogosDao implements IJogosDao {
	
	private GenericDao gDao;
	
	public JogosDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	public List<Jogos> geraRodada() throws SQLException, ClassNotFoundException {
		List<Jogos> listaJogos;
		Connection c = gDao.getConnection();

		String sql = "{CALL sp_criando_rodadas}";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.execute();
		listaJogos = findRodada(c);
		cs.close();
		c.close();

		return listaJogos;

	}


	public List<Jogos> findRodada(Connection c) throws SQLException, ClassNotFoundException {
		
		
		List<Jogos> listaJogos = new ArrayList<Jogos>();
		String sql = "SELECT codigoTimeA, codigoTimeB, golsTimeA, golsTimeB, data FROM jogos";
		PreparedStatement ps = c.prepareStatement(sql);
		//ps.setString(1, j.getData());
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			
			Jogos j = new Jogos();
			
			j.setCodigoTimeA(rs.getInt("codigoTimeA"));
			j.setCodigoTimeB(rs.getInt("codigoTimeB"));
			j.setGolsTimeA(rs.getInt("golsTimeA"));
			j.setGolsTimeB(rs.getInt("golsTimeB"));
			j.setData(rs.getString("data"));
			
			listaJogos.add(j);
			
		}

		rs.close();
		ps.close();
		c.close();

		return listaJogos;
	}

//	@Override
//	public List<Jogos> gerarJogos() throws SQLException, ClassNotFoundException {
//		List<Jogos> listaJogos;
//		Connection c = gDao.getConnection();
//
//		String sql = "{CALL sp_criando_rodadas}";
//		CallableStatement cs = c.prepareCall(sql);
//
//		cs.execute();
//		//listaJogos = findRodada(j);
//		cs.close();
//		c.close();
//
//		return listaJogos;
//	}

//	public Jogos findRodada(Jogos j) throws SQLException, ClassNotFoundException {
//		Connection c = gDao.getConnection();
//		//List<Jogos> listaJogos = new ArrayList<Jogos>();
//		String sql = "SELECT codigoTimeA, codigoTimeB, golsTimeA, golsTimeB, data FROM jogos";
//		PreparedStatement ps = c.prepareStatement(sql);
//		//ps.setInt(1, j.getCodigoTimeA());
//		ResultSet rs = ps.executeQuery();
//		while (rs.next()) {
//		//	Jogos j = new Jogos();
//			
//			j.setCodigoTimeA(rs.getInt("codigoTimeA"));
//			j.setCodigoTimeB(rs.getInt("codigoTimeB"));
//			j.setGolsTimeA(rs.getInt("golsTimeA"));
//			j.setGolsTimeB(rs.getInt("golsTimeB"));
//			j.setData(rs.getString("data"));
//			
//			//listaJogos.add(j);
//		}
//		
//		rs.close();
//		ps.close();
//		c.close();
//		
//		return j;
//	}

//	@Override
//	public String iudRodada(Jogos j) throws SQLException, ClassNotFoundException {
//		Connection con = gDao.getConnection();
//
//		String sql = "{CALL sp_criando_rodadas (?)}";
//		CallableStatement cs = con.prepareCall(sql);
//		cs.registerOutParameter(1, Types.VARCHAR);
//		cs.execute();
//		
//		String saida = cs.getString(1);
//		
//		cs.close();
//		con.close();
//		
//		return saida;
//	}

}
