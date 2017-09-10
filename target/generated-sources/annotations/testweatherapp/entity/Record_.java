package testweatherapp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Record.class)
public abstract class Record_ {

	public static volatile SingularAttribute<Record, Date> timeStamp;
	public static volatile SingularAttribute<Record, String> country;
	public static volatile SingularAttribute<Record, String> city;
	public static volatile SingularAttribute<Record, Float> latitude;
	public static volatile SingularAttribute<Record, Integer> temperature;
	public static volatile SingularAttribute<Record, Integer> id;
	public static volatile SingularAttribute<Record, WeatherCond> rainCond;
	public static volatile SingularAttribute<Record, SwfUser> userId;
	public static volatile SingularAttribute<Record, Float> longitude;
	public static volatile SingularAttribute<Record, WeatherCond> sunCond;

}

