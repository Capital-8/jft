/**
 * 
 */
package com.cmm.jft.services.security;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;

import com.cmm.jft.db.DBFacade;
import com.cmm.jft.db.exceptions.DataBaseException;
import com.cmm.jft.security.Security;
import com.cmm.logging.Logging;

/**
 * <p>
 * <code>SecurityService.java</code>
 * </p>
 * 
 * @author Cristiano M Martins
 * @version 04/08/2014 01:43:12
 *
 */
public class SecurityService {

	private HashMap<String, Security> securities;
	private static SecurityService instance;

	/**
     * 
     */
	private SecurityService() {
		restartSecurityList();
	}

	public static synchronized SecurityService getInstance() {
		if (instance == null) {
			instance = new SecurityService();
		}
		return instance;
	}

	private Security loadSecurity(String symbol) {
		Security sec = null;

		try {
			if(!securities.containsKey(symbol)){
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("symbol", symbol);
	
				List rs = DBFacade.getInstance().queryNamed(
						"Security.findBySymbol", params);
				if (rs != null && !rs.isEmpty()) {
					sec = (Security) rs.get(0);
					securities.put(symbol, sec);
				}
			}
			
			sec = securities.get(symbol);
			
		} catch (DataBaseException e) {
			Logging.getInstance().log(getClass(), e, Level.ERROR);
		}
		return sec;
	}

	public Security provideSecurity(String symbol) {

		Security s = null;
		s = loadSecurity(symbol);
		try {
			if (s == null && !DBFacade.getInstance().getConnection().isClosed()) {
				s = new Security(symbol);
				s = (Security) DBFacade.getInstance()._persist(s);
			}
		} catch (SQLException e) {
			Logging.getInstance().log(getClass(), e, Level.ERROR);
		} catch (DataBaseException e) {
			Logging.getInstance().log(getClass(), e, Level.ERROR);
		}
		
		return s;
	}
	
	
	private void loadSecurityList(){
		try {
			List<Security> data = (List<Security>) DBFacade.getInstance().queryNamed("Security.findAll", null);
			data.parallelStream().forEach(s ->
				securities.put(s.getSymbol(), s)
			);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
	}
	
	public List<Security> getSecurityList(){
		List<Security> data = new LinkedList<>();
		securities.values().forEach(s -> data.add(s));
		return data;
	}
	
	
	public void restartSecurityList(){
		securities = new HashMap<>();
		loadSecurityList();
	}
	

}
