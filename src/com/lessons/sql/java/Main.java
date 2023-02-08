package com.lessons.sql.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/nations";
		String user = "root";
		String password = "root";
		
		//Connection con = null;
		//con = DriverManager.getConnection(url, user, password);
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			
			String sql = 
					"SELECT countries.name, countries.country_id, regions.name, continents.name\r\n"
					+ "FROM countries\r\n"
					+ "INNER JOIN regions ON countries.region_id = regions.region_id\r\n"
					+ "INNER JOIN continents ON regions.continent_id = continents.continent_id\r\n"
					+ "GROUP BY countries.country_id, regions.name, continents.name\r\n"
					+ "ORDER BY countries.name;";
			
			try(PreparedStatement ps = con.prepareStatement(sql)) {
			
				try(ResultSet rs = ps.executeQuery()) {
					System.out.println("NOME STATO \t\t ID STATO \t\t NOME REGIONE \t\t NOME CONTINENTE");
					while (rs.next()) {
						System.out.println(
								rs.getString("countries.name") + "\t\t" +
								rs.getInt("countries.country_id") + "\t\t\t" +
								rs.getString("regions.name") + "\t\t" +
								rs.getString("continents.name")
						);
					}
				}	
			}
		
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
	}

}
