package testweatherapp.repo;

import org.springframework.data.repository.CrudRepository;

import testweatherapp.entity.Record;

public interface RecordRepository extends CrudRepository<Record, Integer>{
	
	

}
