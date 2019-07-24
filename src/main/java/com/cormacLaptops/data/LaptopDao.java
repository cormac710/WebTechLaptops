package com.cormacLaptops.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cormacLaptops.model.Laptop;

@Stateless
@LocalBean
public class LaptopDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Laptop> getLaptops() {
		final Query query = entityManager.createQuery("SELECT l FROM Laptop l");
		return query.getResultList();
	}

	public Laptop getLaptop(final int LaptopId) {
		return entityManager.find(Laptop.class, LaptopId);
	}

	public List<Laptop> getLaptopByMake(final String make) {
		final Query query = entityManager.createQuery("SELECT l FROM Laptop l "
				+ "WHERE l.make LIKE :make")
				.setParameter("make", make);
		return query.getResultList();
	}

	public List<Laptop> getLaptopsByRam(final int minRam, final int maxRam) {
		final Query query = entityManager
				.createQuery("SELECT l FROM Laptop l " 
						+ "WHERE l.ram >= :minRam AND "
						+ "l.ram <= :maxRam")
				.setParameter("minRam", minRam).setParameter("maxRam", maxRam);
		return query.getResultList();
	}

	public void addLaptop(final Laptop laptop) {
		entityManager.persist(laptop);
	}

	public void update(final Laptop laptop) {
		entityManager.merge(laptop);
	}

	public void removeLaptop(final int laptopId) {
		entityManager.remove(getLaptop(laptopId));
	}

}
