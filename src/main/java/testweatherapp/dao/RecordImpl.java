package testweatherapp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import testweatherapp.entity.Record;
import testweatherapp.entity.WeatherCond;
import testweatherapp.entity.Record_;

@Repository
public class RecordImpl implements RecordDAO {

	// @Autowired
	// private SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public double weather(String city, String country) {
		// Session session = sessionFactory.getCurrentSession();
		/*
		 * Query<Double> q = session
		 * .createQuery("select avg(rec.temperature) from Record rec " +
		 * "where rec.timeStamp between :lasthour and :now" +
		 * " and rec.city = :city and rec.country = :country" );
		 */
		TypedQuery<Tuple> criteriaQ = null;
		TypedQuery<Double> q = (TypedQuery<Double>) em.createQuery(
				"select avg(rec.temperature) from Record rec " + "where rec.timeStamp between :lasthour and :now"
						+ " and rec.city = :city and rec.country = :country");
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		Date now = null;
		Date lastHour = null;
		Double average = null;
		try {
			now = dt1.parse(dt1.format(new Date()));
			// q.setParameter("now", now);
			lastHour = dt1.parse(dt1.format(new Date(System.currentTimeMillis() - 3600 * 1000)));
			// q.setParameter("lasthour", lastHour);
			// q.setParameter("city", city);
			// q.setParameter("country", country);
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
			Root<Record> recordRoot = criteriaQuery.from(Record.class);
			Predicate criteria = criteriaBuilder.conjunction();
			if (city != null) {
				Predicate p = criteriaBuilder.equal(recordRoot.get(Record_.city), city);
				criteria = criteriaBuilder.and(criteria, p);
			}
			if (country != null) {
				Predicate p = criteriaBuilder.equal(recordRoot.get(Record_.country), country);
				criteria = criteriaBuilder.and(criteria, p);
			}

			Predicate periodPredicate = criteriaBuilder.between(recordRoot.get(Record_.timeStamp), now, lastHour);
			criteria = criteriaBuilder.and(criteria, periodPredicate);
			Selection<Double> avg = criteriaBuilder.avg(recordRoot.get(Record_.temperature));
			criteriaQuery.multiselect(avg);
			// criteriaQuery.where(criteria);
			criteriaQ = em.createQuery(criteriaQuery);

			System.out.println(dt1.format(new Date()));
			// List<Double> res = q.getResultList();//
			// Double res = criteriaQ.getSingleResult();
			final List<Tuple> list = criteriaQ.getResultList();
			for (Tuple tuple : list) {
				average = tuple.get(avg);
				break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// if (!res.isEmpty()) {
		// return Double.valueOf(res.get(0));
		// } else
		// return 0;
		return average;
	}

	@Transactional
	public int cond(String city, String country) {
		// Session session = sessionFactory.getCurrentSession();
		// Query q = session
		// .createQuery("select rec.sunCond, rec.rainCond from Record rec " +
		// "where rec.timeStamp between :lasthour and :now" +
		// " and rec.city = :city and rec.country = :country" );
		javax.persistence.Query q = em.createQuery(
				"select rec.sunCond, rec.rainCond from Record rec " + "where rec.timeStamp between :lasthour and :now"
						+ " and rec.city = :city and rec.country = :country");
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		Date now = null;
		Date lastHour = null;
		try {
			now = dt1.parse(dt1.format(new Date()));
			q.setParameter("now", now);
			lastHour = dt1.parse(dt1.format(new Date(System.currentTimeMillis() - 3600 * 1000)));
			q.setParameter("lasthour", lastHour);
			q.setParameter("city", city);
			q.setParameter("country", country);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(dt1.format(new Date()));
		List<Object[]> res = q.getResultList();
		int[] conditions = new int[7];
		for (int i = 0; i < res.size(); i++) {
			WeatherCond s = (WeatherCond) res.get(i)[0];
			WeatherCond r = (WeatherCond) res.get(i)[1];
			conditions[s.getId() - 1]++;
			conditions[r.getId() - 1]++;
		}
		int max = 0;
		for (int i = 0; i < conditions.length; i++) {
			if (conditions[i] > conditions[max])
				max = i;
		}
		return max;
	}

	@Transactional
	public Record saveRecord(Record record) {
		// Session session = sessionFactory.getCurrentSession();
		em.persist(record);
		// session.save(record);
		// return session.get(Record.class, record.getId());
		return em.find(Record.class, record.getId());
	}

}
