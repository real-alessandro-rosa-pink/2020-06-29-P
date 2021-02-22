package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Partita;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getMesi() {
		String sql = "SELECT DISTINCT MONTH(m.Date) AS mese " + 
					 "FROM matches AS m " + 
					 "ORDER BY mese";
		List<Integer> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add((res.getInt("mese")));
			}
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void popolaIdMapVertici(Map<Integer, Partita> idMapPartiteTutte) {
		String sql = "SELECT DISTINCT m.MatchID, m.TeamHomeID, m.TeamAwayID, t1.Name AS TeamHomeName, t2.Name AS TeamAwayName " + 
					 "FROM matches AS m, teams AS t1, teams AS t2 " + 
					 "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Partita partita = new Partita( res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getString("TeamHomeName"), res.getString("TeamAwayName"));
				idMapPartiteTutte.put(res.getInt("m.MatchID"), partita);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getVertici(Integer mese) {
		String sql = "SELECT MatchID " + 
					 "FROM matches " + 
					 "WHERE MONTH(Date) = ?";
		List<Integer> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getInt("MatchID"));
			}
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(Integer timePlayed, Integer mese, Map<Integer, Partita> idMapPartiteTutte) {
		String sql = "SELECT m1.MatchID as id_partita_1, m2.MatchID as id_partita_2, COUNT(DISTINCT a1.PlayerID, a2.PlayerID) AS peso " + 
					 "FROM actions AS a1, actions AS a2, matches AS m1, matches AS m2 " + 
					 "WHERE a1.TimePlayed >= ? AND a2.TimePlayed >= ?  " + 
					 "AND MONTH(m1.Date) = ? AND MONTH(m2.Date) = ? " + 
					 "AND a1.PlayerID = a2.PlayerID " + 
					 "AND a1.MatchID = m1.MatchID " + 
					 "AND a2.MatchID = m2.MatchID " + 
					 "AND m1.MatchID > m2.MatchID " + 
					 "GROUP BY m1.MatchID, m2.MatchID";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, timePlayed);
			st.setInt(2, timePlayed);
			st.setInt(3, mese);
			st.setInt(4, mese);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Adiacenza arco = new Adiacenza( idMapPartiteTutte.get(res.getInt("id_partita_1")), idMapPartiteTutte.get(res.getInt("id_partita_2")), res.getInt("peso") );
				result.add(arco);
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
