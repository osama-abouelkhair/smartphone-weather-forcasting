package testweatherapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RECORD")
public class Record implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TEMPERATURE")
	private int temperature;

	@Column(name = "LATITUDE")
	private float latitude;

	@Column(name = "LONGITUDE")
	private float longitude;
	
	@Column(name = "CITY")
	String city;
	
	@Column(name = "COUNTRY")
	String country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUN_COND")
	private WeatherCond sunCond;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RAIN_COND")
	private WeatherCond rainCond;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private SwfUser userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME_STAMP", nullable = false)
	private Date timeStamp;
	
	@PrePersist
	protected void onUpdate(){
		timeStamp = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public WeatherCond getSunCond() {
		return sunCond;
	}

	public void setSunCond(WeatherCond sunCond) {
		this.sunCond = sunCond;
	}

	public WeatherCond getRainCond() {
		return rainCond;
	}

	public void setRainCond(WeatherCond rainCond) {
		this.rainCond = rainCond;
	}

	public SwfUser getUserId() {
		return userId;
	}

	public void setUserId(SwfUser userId) {
		this.userId = userId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
