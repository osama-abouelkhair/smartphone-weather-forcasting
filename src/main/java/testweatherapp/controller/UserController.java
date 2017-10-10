package testweatherapp.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;

import testweatherapp.dao.SwfUserDAO;
import testweatherapp.entity.SwfUser;
/**
 * 
 * @author Osama
 * lkdlkdsffd
 * kf
 ** lkdlkdsffd
 * kf* lkdlkdsffd
 * kf* lkdlkdsffd
 * kf* lkdlkdsffd
 * kf* lkdlkdsffd
 * kf
 */

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired 
	private SwfUserDAO userDAO;
	
	@RequestMapping("/list")
	public Collection<SwfUser> usersList(Model model){
		List<SwfUser> users = userDAO.getSwfUsers();
		//model.addAttribute("users", users);
		return users;
	}
	
	@RequestMapping(value = "/register", 
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> registerUser(@RequestBody SwfUser user, ModelMap model){
		//SwfUser user=new Gson().fromJson((String) params.get("user"), SwfUser.class);
		//SwfUser user = new SwfUser();
		//user.setSn("123456");
		SwfUser verfiedUser;
		URI userURI = null;
		if(user != null){
			try{
			verfiedUser = userDAO.registerUser(user);
			userURI = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.buildAndExpand(verfiedUser.getId()).toUri();
			model.addAttribute("userID",verfiedUser.getId());
			} catch(Exception e){
				model.addAttribute("exception", e.getStackTrace());
			}
		}
		return ResponseEntity.created(userURI).build();
	}
	
	
	@RequestMapping(value = "/login", 
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody SwfUser user, Model model){
		//SwfUser user=new Gson().fromJson((String) params.get("user"), SwfUser.class);
		SwfUser verifiedUser = userDAO.login(user);
		URI userURI = null;
		if(verifiedUser == null){
			verifiedUser = userDAO.registerUser(user);
			userURI = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.buildAndExpand(verifiedUser.getId()).toUri();
		}
		//model.addAttribute("user", verifiedUser);
	    return ResponseEntity.created(userURI).build();

	}
	
}
