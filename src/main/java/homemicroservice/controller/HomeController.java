package homemicroservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import homemicroservice.dao.HomeDAO;
import homemicroservice.domains.Home;
import homemicroservice.domains.User;

@RestController
@CrossOrigin
public class HomeController {

	@Autowired
	HomeDAO homeDAO;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@RequestMapping("/homes")
	public List<Home> getHomes(){
		return homeDAO.findAll();
	}
	
	@RequestMapping("/homes/{id}")
	public Home getHomeByID(@PathVariable long id) {
		Home home = homeDAO.findByHomeID(id);
		return home;
	}
	
	@RequestMapping("/homes/find/user/{hostid}")
	public List<Home> getHomesForHost(@PathVariable long hostid){
		List<Home> hosthome = homeDAO.findByHost(hostid);
		return hosthome;
	}
	
	@RequestMapping("/homes/find/{name}") 
	public Home getHomeByName(@PathVariable String name){
		Home home = homeDAO.findByName(name);
		return home;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/homes/search")
	public ResponseEntity<List<Home>> searchHomes(@RequestBody String jsonString){
		String query = "SELECT * FROM Home WHERE LOWER(name) LIKE ";
		JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
		String name = jobj.get("name").getAsString();
	
		String pattern = "'%" + name.toLowerCase() + "%'";
//		
		query += pattern;
//		
//		String priceparse;
//		switch (price) {
//		case 0:
//			priceparse = " ";
//			break;
//		case 1:
//			priceparse = " AND h.price < 35 ";
//			break;
//		case 2:
//			priceparse = " AND h.price > 35 AND h.price < 69 ";
//			break;
//		case 3:
//			priceparse = " AND h.price > 70 AND h.price < 130 ";
//			break;
//		case 4:
//			priceparse = " AND h.price > 131 ";
//			break;
//		default:
//			priceparse = " ";
//			break;
//		}
//		
//		query += priceparse;
//		
//		query += "AND h.date_available_start<= " + start_date + " AND h.date_available_end>= " + end_date + "AND h.number_of_guests >= ";
//		
//		int numGuests = adults + kids;
//		
//		query += numGuests;
		
		System.out.println(query);
		ResponseEntity<List<Home>> response;
		
		try{
			List<Home> listHomes = jdbc.query(query, new BeanPropertyRowMapper(Home.class));
			response = new ResponseEntity<List<Home>>(listHomes, HttpStatus.OK);
		} catch (Exception e){
			e.printStackTrace();
			response = new ResponseEntity<List<Home>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	/*
	 * SAMPLE JDBC SEARCH WITH TELMO'S CODE
	 * @RequestMapping (method = RequestMethod.POST, value="/users/jdbc")
	public ResponseEntity<List<User>> jdbcSearch(){
		
		String query = "SELECT * FROM user";
		query+=" where name = '"+"Frodo"+"'";
			
		System.out.println("[ADVANCED SEARCH][USERS] ["+query+"]");
		ResponseEntity<List<User>> response;
		
		try{
			// To save having to map the data returned as rows to the attributes
			// the beans use BeanPropertyRowMapper. Just make sure that the names
			// of the columns and the attributes match
			List<User> luser  = jdbc.query(query,new BeanPropertyRowMapper(User.class));
			
			response = new ResponseEntity<List<User>>(luser, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			response = new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}
	 * 
	 * 
	 */
	
//	@RequestMapping("/homes/find/{name}/{start_date}/{end_date}/{price}/{type}/{adults}/{kids}")
//	public List<Home>findHome(@PathVariable String name, @PathVariable Date start_date, @PathVariable Date end_date, @PathVariable int price, @PathVariable int type, @PathVariable int adults, @PathVariable int kids){
//		String queryString = "SELECT h FROM Home h WHERE LOWER(h.name) LIKE :pattern AND h.date_available_start<=:start_date AND h.date_available_end>=:end_date AND h.number_of_guests >= :number_of_guests";
//		
//		return null;
//		
//	}
//	
	@RequestMapping(method = RequestMethod.POST, value="/homes")
	public Home saveHome (@RequestBody @Validated Home home){
		return homeDAO.save(home);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/homes/{id}")
	public void deleteHome (@PathVariable Long id) {
		homeDAO.deleteById(id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/homes/{id}")
	public Home updateHome (@PathVariable Long id, @RequestBody @Validated Home home){
		Home homeDB;
		if (homeDAO.findByHomeID(id) != null) {
			homeDB = homeDAO.findByHomeID(id);
		} else {
			homeDB = null;
		}
		if (home != null){
			if (home.getName() != null) {
				homeDB.setName(home.getName());
			}
			if (home.getFull_description() != null) {
				homeDB.setFull_description(home.getFull_description());
			}
			if (home.getShort_description() != null) {
				homeDB.setShort_description(home.getShort_description());
			}
			if (home.getType() == 0 || home.getType() == 1 || home.getType() == 2 || home.getType() == 3){
				homeDB.setType(home.getType());
			} else {
				homeDB.setType(0);
			}
			if (home.getNumber_of_guests() != 0){
				homeDB.setNumber_of_guests(home.getNumber_of_guests());
			} else {
				homeDB.setNumber_of_guests(1);
			}
			if (home.getBookings1() != null) {
				homeDB.setBookings1(home.getBookings1());
			}
			if (home.getPrice() >= 35) {
				homeDB.setPrice(home.getPrice());
			} else {
				home.setPrice(35);
			}
			if (home.getImage() != null) {
				homeDB.setImage(home.getImage());
			}
			if (home.getDate_available_start() != null) {
				homeDB.setDate_available_start(home.getDate_available_start());
			}
			if (home.getDate_available_end() != null) {
				homeDB.setDate_available_end(home.getDate_available_end());
			}
			if (home.getUser() != null) {
				homeDB.setUser(home.getUser());
			}
			return homeDAO.save(home);
		}
		return null;
	}
	
	
	
	
}
