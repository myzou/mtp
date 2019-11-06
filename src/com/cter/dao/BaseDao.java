package com.cter.dao;

import java.io.Serializable;
import java.util.List;

/**
 * �������ݿ������
 * 
 * @author www.java1234.com
 * 
 */
public interface BaseDao<T> {

	/**
	 * ����һ������T
	 * 
	 * @param o
	 * @return
	 */
	public Serializable save(T o);
	/**
	 * ����һ������
	 * 
	 * @param o
	 * @return
	 */
	public Serializable saveO(Object o);
	/**
	 * ɾ��һ������
	 * 
	 * @param o
	 */
	public void delete(T o);

	/**
	 * ɾ��һ������Object
	 * 
	 * @param o
	 */
	public void deleteO(Object o);
	/**
	 * ����һ������
	 * 
	 * @param o
	 */
	public void update(T o);
	/**
	 * ����һ������
	 * 
	 * @param o
	 */
	public void updateO(Object o);

	/**
	 * �������¶���
	 * 
	 * @param o
	 */
	public void saveOrUpdate(T o);

	/**
	 * ��ѯ
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql);

	/**
	 * ��ѯ����
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<T> find(String hql, Object[] param);

	/**
	 * ��ѯ���� Object
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<Object> findO(String hql, Object[] param);
	
	/**
	 * ��ѯ���� Object
	 * @param hql
	 * @param List<Object>
	 * @return
	 */
	public List<Object> findO(String hql, List<Object> param);
	/**
	 * ��ѯ����
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<T> find(String hql, List<Object> param);

	/**
	 * ��ѯ����(����ҳ)
	 * 
	 * @param hql
	 * @param param ������ Object ����
	 * @param page  ��ѯ�ڼ�ҳ
	 * @param rows  ÿҳ��ʾ������¼
	 * @return
	 */
	public List<T> find(String hql, Object[] param, Integer page, Integer rows);
	
	

	/**
	 * ��ѯ����(����ҳ)T
	 * 
	 * @param hql
	 * @param param ������List<Object>
	 * @param page  ��ѯ�ڼ�ҳ
	 * @param rows  ÿҳ��ʾ������¼
	 * @return
	 */
	public List<T> find(String hql, List<Object> param, Integer page, Integer rows);
	
	/**
	 * ��ѯ����(����ҳ)Object
	 * 
	 * @param hql
	 * @param param ������List<Object>
	 * @param page  ��ѯ�ڼ�ҳ
	 * @param rows  ÿҳ��ʾ������¼
	 * @return   ���ص��Ƕ���
	 */
	public List<Object> findO(String hql, List<Object> param, Integer page, Integer rows);


	
	
	/**
	 * ���һ������
	 * 
	 * @param c
	 *            ��������
	 * @param id
	 * @return Object
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * ���һ������
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 */
	public T get(String hql, Object[] param);

	/**
	 * ���һ������
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public T get(String hql, List<Object> param);

	/**
	 * ���ݴ������ı��� ��ѯ��Ӧ��������
	 * @param hql
	 * @return
	 */
	public int countO(String tableName);
	
	/**
	 * ����hql ��ѯ��ҳ����
	 * @param hql
	 * @param param ����
	 * @return
	 */
	public int countHql(String hql,List<Object> param);
	
	/**
	 * ͳ�Ƶ�ǰ�̳�������
	 * @param hql
	 * @return
	 */
	public int count();

	/**
	 * select count(*) from ��
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, Object[] param);

	/**
	 * select count(*) from ��
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, List<Object> param);

	/**
	 * ִ��HQL���
	 * 
	 * @param hql
	 * @return ��Ӧ��Ŀ
	 */
	public Integer executeHql(String hql);

	/**
	 * ִ��HQL���
	 * 
	 * @param hql
	 * @param param
	 * @return ��Ӧ��Ŀ
	 */
	public Integer executeHql(String hql, Object[] param);

	/**
	 * ִ��HQL���
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Integer executeHql(String hql, List<Object> param);
	
	/**
	 * ִ��sql��� ��ѯ��List<String>����
	 * @param sql
	 * @return
	 */
	public List<String> queryBySqlStrList(String sql) ;

	/**
	 * ִ��sql��� ��ѯ��List<T>����
	 * @param sql
	 * @return
	 */
	public List<T> queryBySqlT(String sql);
	


}
