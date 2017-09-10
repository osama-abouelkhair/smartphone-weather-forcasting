package testweatherapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import testweatherapp.dao.RecordDAO;
import testweatherapp.dao.SwfUserDAO;
import testweatherapp.entity.Record;
import testweatherapp.entity.SwfUser;
import testweatherapp.entity.WeatherCond;

@RestController
public class WeatherController {

	@Autowired
	private RecordDAO recordDAO;
	@Autowired
	private SwfUserDAO userDAO;

	@RequestMapping(value = "/temperature", produces = "application/json;charset=UTF-8")
	public ModelAndView weather(@RequestParam(value="latitude",required=true) float latitude, 
			@RequestParam(value="longitude",required=true) float longitude) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("weather");
		
		//float latitude = Float.valueOf(params.get("latitude"));
		//float longitude = Float.valueOf(params.get("longitude"));
		
		if (latitude != 0 && longitude != 0) {
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + ","
					+ longitude + "&sensor=true";
			ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
			JsonElement jelement = new JsonParser().parse(response.getBody());
			JsonObject results = jelement.getAsJsonObject();
			JsonArray location = results.getAsJsonArray("results");
			JsonObject address = location.get(0).getAsJsonObject();
			JsonArray comp = address.getAsJsonArray("address_components");

			// get city from the returned JSON
			JsonObject cityObj = comp.get(comp.size()-2).getAsJsonObject();
			String city = cityObj.get("long_name").toString();
			// get country from the returned JSON
			JsonObject countryObj = comp.get(comp.size()-1).getAsJsonObject();
			String country = countryObj.get("long_name").toString();
			double temp = recordDAO.weather(city, country);
			// model.addAttribute("temperature", temp);
			//return new ResponseEntity<String>(String.valueOf(temp), HttpStatus.OK);
			
			modelAndView.addObject("temperature", temp);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/rest/temperature", produces = "application/json;charset=UTF-8")
	public ResponseEntity weather2(@RequestParam(value="latitude",required=true) float latitude, 
			@RequestParam(value="longitude",required=true) float longitude) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("weather");
		
		//float latitude = Float.valueOf(params.get("latitude"));
		//float longitude = Float.valueOf(params.get("longitude"));
		double temp = 0;
		if (latitude != 0 && longitude != 0) {
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + ","
					+ longitude + "&sensor=true";
			ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
			JsonElement jelement = new JsonParser().parse(response.getBody());
			JsonObject results = jelement.getAsJsonObject();
			JsonArray location = results.getAsJsonArray("results");
			JsonObject address = location.get(0).getAsJsonObject();
			JsonArray comp = address.getAsJsonArray("address_components");

			// get city from the returned JSON
			JsonObject cityObj = comp.get(comp.size()-2).getAsJsonObject();
			String city = cityObj.get("long_name").toString();
			// get country from the returned JSON
			JsonObject countryObj = comp.get(comp.size()-1).getAsJsonObject();
			String country = countryObj.get("long_name").toString();
			temp = recordDAO.weather(city, country);
			// model.addAttribute("temperature", temp);
			//return new ResponseEntity<String>(String.valueOf(temp), HttpStatus.OK);
			
			modelAndView.addObject("temperature", temp);
		}
		return new ResponseEntity<>(temp, HttpStatus.OK);
	}
			

	@RequestMapping(value = "/cond", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> cond(@RequestParam Map<String, String> params, Model model) {
		float latitude = Float.valueOf(params.get("latitude"));
		float longitude = Float.valueOf(params.get("longitude"));
		if (latitude != 0 && longitude != 0) {
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + ","
					+ longitude + "&sensor=true";
			ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
			JsonElement jelement = new JsonParser().parse(response.getBody());
			JsonObject results = jelement.getAsJsonObject();
			JsonArray location = results.getAsJsonArray("results");
			JsonObject address = location.get(0).getAsJsonObject();
			JsonArray comp = address.getAsJsonArray("address_components");

			// get city from the returned JSON
			JsonObject cityObj = comp.get(comp.size()-2).getAsJsonObject();
			String city = cityObj.get("long_name").toString();
			// get country from the returned JSON
			JsonObject countryObj = comp.get(comp.size()-1).getAsJsonObject();
			String country = countryObj.get("long_name").toString();
			int condID = recordDAO.cond(city, country);
			// model.addAttribute("temperature", temp);
			return new ResponseEntity<String>(String.valueOf(condID), HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/record", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ModelAndView record(@RequestParam(value="latitude",required=true) float latitude, 
			@RequestParam(value="longitude",required=true) float longitude) {
		// Gson gSon = new
		// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		// Record record = gSon.from((String) params.get("record"),
		// Record.class);
		// Gson gson = new Gson();

		// JsonElement jsonElement = gson.toJsonTree(params);
		// How to perform this without JSON serialization?
		// Record record = gson.fromJson(jsonElement, Record.class);
		Random r = new Random();
		List<Record> temps = new ArrayList<>();

		Record record;
		WeatherCond sunCond;
		WeatherCond rainCond;
		SwfUser user;

		user = new SwfUser();
		//user.setId(1);
		user.setSn("12345");
		userDAO.registerUser(user);
		
		for (int i = 0; i < 10; i++) {
			record = new Record();
			//sunCond = new WeatherCond();
			//rainCond = new WeatherCond();

			// sunCond.setId(Integer.valueOf(params.get("sun_cond")));
			// rainCond.setId(Integer.valueOf(params.get("rain_cond")));
			// user.setId(Integer.valueOf(params.get("userId")));
			//record.setSunCond(sunCond);
			//record.setRainCond(rainCond);
			record.setUserId(user);

			// record.setLatitude(Float.valueOf(params.get("latitude")));
			record.setLatitude(latitude);
			// record.setLongitude(Float.valueOf(params.get("longitude")));
			record.setLongitude(longitude);
			// record.setTemperature((Float.valueOf(params.get("temp")).intValue()));
			record.setTemperature(r.nextInt(40 - 20) + 20);
			// record.setTimeStamp(Date.parse(params.get("temp")));

			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
			Date now = null;
			try {
				now = dt1.parse(dt1.format(new Date()));
				record.setTimeStamp(now);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (record != null) {
				if (record.getLatitude() != 0 && record.getLongitude() != 0) {
					RestTemplate restTemplate = new RestTemplate();
					String fooResourceUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ record.getLatitude() + "," + record.getLongitude() + "&sensor=true";
					ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
					JsonElement jelement = new JsonParser().parse(response.getBody());
					JsonObject results = jelement.getAsJsonObject();
					JsonArray location = results.getAsJsonArray("results");
					JsonObject address = location.get(0).getAsJsonObject();
					JsonArray comp = address.getAsJsonArray("address_components");

					// get city from the returned JSON
					JsonObject cityObj = comp.get(comp.size()-2).getAsJsonObject();
					String city = cityObj.get("long_name").toString();
					// get country from the returned JSON
					JsonObject countryObj = comp.get(comp.size()-1).getAsJsonObject();
					String country = countryObj.get("long_name").toString();
					record.setCity(city);
					record.setCountry(country);
					Record savedRecord = recordDAO.saveRecord(record);

					// model.addAttribute("status", "recorded");
					// return "index";
					temps.add(savedRecord);
				}
			}
			// model.addAttribute("user", savedRecord);
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("forward:/temperature");
		model.addObject("temps", temps);
		return model;
	}
	@RequestMapping(value = "/rest/record", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity record2(@RequestParam(value="latitude",required=true) float latitude, 
			@RequestParam(value="longitude",required=true) float longitude) {
		// Gson gSon = new
		// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		// Record record = gSon.from((String) params.get("record"),
		// Record.class);
		// Gson gson = new Gson();

		// JsonElement jsonElement = gson.toJsonTree(params);
		// How to perform this without JSON serialization?
		// Record record = gson.fromJson(jsonElement, Record.class);
		Random r = new Random();
		List<Record> temps = new ArrayList<>();

		Record record;
		WeatherCond sunCond;
		WeatherCond rainCond;
		SwfUser user;

		user = new SwfUser();
		//user.setId(1);
		user.setSn("12345");
		userDAO.registerUser(user);
		
		for (int i = 0; i < 10; i++) {
			record = new Record();
			//sunCond = new WeatherCond();
			//rainCond = new WeatherCond();

			// sunCond.setId(Integer.valueOf(params.get("sun_cond")));
			// rainCond.setId(Integer.valueOf(params.get("rain_cond")));
			// user.setId(Integer.valueOf(params.get("userId")));
			//record.setSunCond(sunCond);
			//record.setRainCond(rainCond);
			record.setUserId(user);

			// record.setLatitude(Float.valueOf(params.get("latitude")));
			record.setLatitude(latitude);
			// record.setLongitude(Float.valueOf(params.get("longitude")));
			record.setLongitude(longitude);
			// record.setTemperature((Float.valueOf(params.get("temp")).intValue()));
			record.setTemperature(r.nextInt(40 - 20) + 20);
			// record.setTimeStamp(Date.parse(params.get("temp")));

			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
			Date now = null;
			try {
				now = dt1.parse(dt1.format(new Date()));
				record.setTimeStamp(now);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (record != null) {
				if (record.getLatitude() != 0 && record.getLongitude() != 0) {
					RestTemplate restTemplate = new RestTemplate();
					String fooResourceUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ record.getLatitude() + "," + record.getLongitude() + "&sensor=true";
					ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
					JsonElement jelement = new JsonParser().parse(response.getBody());
					JsonObject results = jelement.getAsJsonObject();
					JsonArray location = results.getAsJsonArray("results");
					JsonObject address = location.get(0).getAsJsonObject();
					JsonArray comp = address.getAsJsonArray("address_components");

					// get city from the returned JSON
					JsonObject cityObj = comp.get(comp.size()-2).getAsJsonObject();
					String city = cityObj.get("long_name").toString();
					// get country from the returned JSON
					JsonObject countryObj = comp.get(comp.size()-1).getAsJsonObject();
					String country = countryObj.get("long_name").toString();
					record.setCity(city);
					record.setCountry(country);
					Record savedRecord = recordDAO.saveRecord(record);

					// model.addAttribute("status", "recorded");
					// return "index";
					temps.add(savedRecord);
				}
			}
			// model.addAttribute("user", savedRecord);
		}
		
		return new ResponseEntity<>(temps, HttpStatus.OK);
	}

}
