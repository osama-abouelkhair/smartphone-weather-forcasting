package testweatherapp.controller;

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

import com.google.gson.Gson;

import testweatherapp.dao.SwfUserDAO;
import testweatherapp.entity.SwfUser;


@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private SwfUserDAO userDAO;
	
	@RequestMapping("/list")
	public String usersList(Model model){
		List<SwfUser> users = userDAO.getSwfUsers();
		model.addAttribute("users", users);
		return "users-list";
	}
	
	@RequestMapping(value = "/register", 
			produces="application/json;charset=UTF-8",
			method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody SwfUser user, ModelMap model){
		//SwfUser user=new Gson().fromJson((String) params.get("user"), SwfUser.class);
		//SwfUser user = new SwfUser();
		//user.setSn("123456");
		if(user != null){
			try{
			SwfUser verfiedUser = userDAO.registerUser(user);
			model.addAttribute("userID",verfiedUser.getId());
			} catch(Exception e){
				model.addAttribute("exception", e.getStackTrace());
			}
		}
		return "index";
	}
	
	
	@RequestMapping(value = "/login", 
			consumes="application/json;charset=UTF-8",
			produces="application/json;charset=UTF-8", 
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SwfUser> login(@RequestBody SwfUser user, Model model){
		//SwfUser user=new Gson().fromJson((String) params.get("user"), SwfUser.class);
		SwfUser verifiedUser = userDAO.login(user);
		if(verifiedUser == null){
			verifiedUser = userDAO.registerUser(user);
		}
		//model.addAttribute("user", verifiedUser);
	    return new ResponseEntity(verifiedUser, HttpStatus.CREATED);

	}
}
