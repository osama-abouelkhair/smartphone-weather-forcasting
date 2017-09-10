package testweatherapp.dao;

import java.util.List;

import testweatherapp.entity.SwfUser;

public interface SwfUserDAO {
	public List<SwfUser> getSwfUsers();
	public SwfUser registerUser(SwfUser user);
	public SwfUser login(SwfUser user);

}


