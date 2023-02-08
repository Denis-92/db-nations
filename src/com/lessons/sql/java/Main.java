package com.lessons.sql.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/nations";
		String user = "root";
		String password = "root";
		
		Scanner inputUtente = new Scanner(System.in);
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			
			System.out.print("Inserisci filtro di ricerca: ");
			String inputFiltro = inputUtente.nextLine();
			String filtroRicerca = "%" + inputFiltro + "%";
			
			 String sql = 
						"SELECT countries.name AS Nome_stato, countries.country_id AS ID_stato, regions.name AS Nome_regione, continents.name AS Nome_continente\r\n"
						+ "FROM countries\r\n"
						+ "INNER JOIN regions ON countries.region_id = regions.region_id\r\n"
						+ "INNER JOIN continents ON regions.continent_id = continents.continent_id\r\n"
						+ "WHERE countries.name LIKE ? \r\n"
						+ "GROUP BY ID_stato, Nome_regione, Nome_continente\r\n"
						+ "ORDER BY Nome_stato;";
			
			try(PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, filtroRicerca);
			
				try(ResultSet rs = ps.executeQuery()) {
					System.out.println("NOME STATO \t\t ID STATO \t\t NOME REGIONE \t\t NOME CONTINENTE");
					while (rs.next()) {
						System.out.println(
								rs.getString("Nome_stato") + "\t\t" +
								rs.getInt("ID_stato") + "\t\t\t" +
								rs.getString("Nome_regione") + "\t\t" +
								rs.getString("Nome_continente")
						);
					}
				}	
			}
		
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		inputUtente.close();
		
	}

}

/*
MILESTONE 1 e 2
 String sql = 
					"SELECT countries.name AS Nome_stato, countries.country_id AS ID_stato, regions.name AS Nome_regione, continents.name AS Nome_continente\r\n"
					+ "FROM countries\r\n"
					+ "INNER JOIN regions ON countries.region_id = regions.region_id\r\n"
					+ "INNER JOIN continents ON regions.continent_id = continents.continent_id\r\n"
					+ "GROUP BY ID_stato, Nome_regione, Nome_continente\r\n"
					+ "ORDER BY Nome_stato;";
*/
