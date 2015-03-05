package ca.app.security.service;

import ca.app.user.dao.GenericDAO;
import ca.app.user.model.User;
import ca.app.user.vo.UserBean;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private GenericDAO<User, Long> userDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class).add(Restrictions.eq("username", username));
		List<User> result = this.userDao.findByCriteria(User.class, criteria);
		if (result == null || result.size() != 1) {
			throw new ObjectNotFoundException(username, User.class.toString());
		} else {
			return new UserBean(result.get(0));
		}
	}
}
