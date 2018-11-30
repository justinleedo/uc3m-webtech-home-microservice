package homemicroservice.domains;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the HOME database table.
 * 
 */
@Entity
@Table(name="HOME")
@NamedQuery(name="Home.findAll", query="SELECT h FROM Home h")
public class Home implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long homeid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date_available_end;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date_available_start;

	private String full_description;

	private String image;

	private String name;

	private int number_of_guests;

	private int price;

	private String short_description;

	private int type;

	//bi-directional many-to-one association to Booking
	@JsonIgnore
	@OneToMany(mappedBy="home")
	private List<Booking> bookings1;


	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Home() {
	}
	
	
	public String toString() {
		return this.name;
	}



	public long getHomeid() {
		return homeid;
	}



	public void setHomeid(long homeid) {
		this.homeid = homeid;
	}



	public Date getDate_available_end() {
		return date_available_end;
	}



	public void setDate_available_end(Date date_available_end) {
		this.date_available_end = date_available_end;
	}



	public Date getDate_available_start() {
		return date_available_start;
	}



	public void setDate_available_start(Date date_available_start) {
		this.date_available_start = date_available_start;
	}



	public String getFull_description() {
		return full_description;
	}



	public void setFull_description(String full_description) {
		this.full_description = full_description;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getNumber_of_guests() {
		return number_of_guests;
	}



	public void setNumber_of_guests(int number_of_guests) {
		this.number_of_guests = number_of_guests;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public String getShort_description() {
		return short_description;
	}



	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public List<Booking> getBookings1() {
		return this.bookings1;
	}

	public void setBookings1(List<Booking> bookings1) {
		this.bookings1 = bookings1;
	}

	public Booking addBookings1(Booking bookings1) {
		getBookings1().add(bookings1);
		bookings1.setHome(this);

		return bookings1;
	}

	public Booking removeBookings1(Booking bookings1) {
		getBookings1().remove(bookings1);
		bookings1.setHome(null);

		return bookings1;
	}


	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
