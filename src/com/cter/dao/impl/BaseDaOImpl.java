package com.cter.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.cter.dao.BaseDao;


@Repository("BaseDaOImpl")
@SuppressWarnings("all")
public class BaseDaOImpl<T> implements BaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * ��ȡ�̳еķ����� T ����
	 * �������Կ��Ը����෽�����л�ȡ
	 * @return
	 */
	public  Class<T>  getClazz(){
		return (Class<T> ) ((ParameterizedType )getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}
	
	public Serializable saveO(Object o) {
		return   this.getCurrentSession().save(o);
	}

	public void deleteO(Object o) {
		this.getCurrentSession().delete(o);
	}
	
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}
	public void updateO(Object o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}
	
	public void saveOrUpdateO(Object o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}
	/**
	 * ��ѯ��ǰ�̳еķ�����ı������list
	 * @return
	 */
	public List<T> findAll() {
		String TClassName=this.getClazz().getSimpleName();
		String hql =" from " + TClassName;
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	
	/**
	 * ��ѯ�������͵ı������list
	 * @return
	 */
	public List<Object> findAll(Class c) {
		String TClassName=c.getSimpleName();
		String hql =" from " + TClassName;
		return this.getCurrentSession().createQuery(hql).list();
	}

	public List<T> find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				if(null!=param[i]){
					q.setParameter(i, param[i]);
				}
			}
		}
		return q.list();
	}
	
	@Override
	public List<Object> findO(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				if(null!= param[i]&&! param[i].equals("")){
					q.setParameter(i, param[i]);
				}
			
			}
		}
		return q.list();
	}
	@Override
	public List<Object> findO(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}
	
	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}
	
	
	public List<T> find(String hql, Object[] param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public List<Object> findO(String hql, List<Object> param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}


	public List<T> find(String hql, List<Object> param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object[] param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public int countO(String tableName) {
		String hql=" select  count(*) from  "+tableName;
		Long long1=(Long) this.getCurrentSession().createQuery(hql).uniqueResult();
		return long1.intValue();
	}
	
	@Override
	public int countHql(String hql,List<Object> param) {
		hql="select count(*)  "+hql;
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		Long long1=(Long) q .uniqueResult();
		return long1.intValue();
	}
	
	@Override
	public int count() {
		String hql=" select  count(*)  from  "+getClazz().getSimpleName();
		Long long1=(Long) this.getCurrentSession().createQuery(hql).uniqueResult();
		return  long1.intValue();
	}
	
	public Long count(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<T> queryBySqlT(String sql) {
		List<T> list= this.getCurrentSession().createSQLQuery(sql).list();
        return list;    
	}
	
	@Override
	public List<String> queryBySqlStrList(String sql) {
		List<String> list= this.getCurrentSession().createSQLQuery(sql).list();
        return list;    
	}

	/**
	 * ����hql�Ͳ�����Class���Ͳ�ѯ���
	 * @param hql
	 * @param param Array��ʽ
	 * @param pojoClass
	 * @return
	 */
	public    <T> List<T> findOByClass(String hql, List<Object> param ,Class<T> pojoClass  ){
		 List<Object>  list=findO(hql, param);
			List<T> list2 = new ArrayList<T>();
			try {
				if (list != null && list.size() > 0) {
					for (Object object : list) {
						list2.add((T) object);
					}
				}
				return list2;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * ����hql�Ͳ�����Class���Ͳ�ѯ���
	 * @param hql
	 * @param param list��ʽ
	 * @param pojoClass
	 * @return
	 */
	public    <T> List<T> findOByClass(String hql, Object[] param ,Class<T> pojoClass  ){
		 List<Object>  list=findO(hql, param);
			List<T> list2 = new ArrayList<T>();
			try {
				if (list != null && list.size() > 0) {
					for (Object object : list) {
						list2.add((T) object);
					}
				}
				return list2;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * ����ʵ��������
	 * ��List���� ת��ΪListʵ����
	 * @param list   List����
	 * @param pojoClass ʵ��������
	 * @return
	 */
	public static  <T> List<T> objectListToPojoList(List<Object> list,Class<T> pojoClass  ){
		List<T> list2 = new ArrayList<T>();
		try {
			if (list != null && list.size() > 0) {
				for (Object object : list) {
					list2.add((T) object);
				}
			}
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
