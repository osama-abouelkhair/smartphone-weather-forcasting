package testweatherapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import testweatherapp.entity.SwfUser;


@Repository
public class SwfUserImpl implements SwfUserDAO{

	//@Autowired
	//private SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<SwfUser> getSwfUsers() {
		//Session session = sessionFactory.getCurrentSession();
		//Query<SwfUser> query = session.createQuery("from SwfUser", SwfUser.class);
		//List<SwfUser> users = query.getResultList();
		//return users;
		return null;
	}
	
	@Transactional
	public SwfUser registerUser(SwfUser user){
		//Session session = sessionFactory.getCurrentSession();
		//session.save(user);
		//return session.get(SwfUser.class, user.getId());
		em.persist(user);
		//em.find(SwfUser.class, user);
		return user;
	}
	
	@Transactional
	public SwfUser login(SwfUser user){
		/*Session session = sessionFactory.getCurrentSession();
		//Criteria cr = session.createCriteria(SwfUser.class);
		//cr.add(Restrictions.eq("sn", user.getSn()));
		Query<SwfUser> q = session.createQuery("from SwfUser where sn = :serialNumber");
		q.setParameter("serialNumber", user.getSn());
		List verifiedUser = q.list();
		if(verifiedUser.size()==0){
			return null;
		} else {
			return q.list().get(0);
		}
	*/return null;
	}	
}
