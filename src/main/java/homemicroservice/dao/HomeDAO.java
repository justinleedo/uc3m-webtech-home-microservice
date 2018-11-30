package homemicroservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import homemicroservice.domains.Home;


public interface HomeDAO extends CrudRepository<Home, Long> {

	@Query("Select h from Home h where h.homeid=:homeid")
	public Home findByHomeID(@Param("homeid") long id);
	
	public Home findByName(String name);
	
	@Query("SELECT h from Home h JOIN h.user u WHERE u.userid =:user_userid")
	public List<Home> findByHost(@Param("user_userid") long id);
	
	public List<Home> findAll();
}
