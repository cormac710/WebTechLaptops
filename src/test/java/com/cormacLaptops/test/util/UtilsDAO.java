package com.cormacLaptops.test.util;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UtilsDAO {

    @PersistenceContext
    private EntityManager entityManager;
    
	public void deleteTable(){
		System.out.println("in");
		entityManager.createQuery("DELETE FROM Laptop").executeUpdate();
	}
      
}
