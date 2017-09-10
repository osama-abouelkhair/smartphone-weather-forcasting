package testweatherapp.dao;

import testweatherapp.entity.Record;

public interface RecordDAO {

	public double weather(String city, String country);

	public Record saveRecord(Record record);

	int cond(String city, String country);
}
