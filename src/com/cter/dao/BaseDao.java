package com.cter.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基础数据库操作类
 * 
 * @author www.java1234.com
 * 
 */
public interface BaseDao<T> {

	/**
	 * 保存一个对象T
	 * 
	 * @param o
	 * @return
	 */
	public Serializable save(T o);
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public Serializable saveO(Object o);
	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	public void delete(T o);

	/**
	 * 删除一个对象Object
	 * 
	 * @param o
	 */
	public void deleteO(Object o);
	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public void update(T o);
	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public void updateO(Object o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	public void saveOrUpdate(T o);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<T> find(String hql, Object[] param);

	/**
	 * 查询集合 Object
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<Object> findO(String hql, Object[] param);
	
	/**
	 * 查询集合 Object
	 * @param hql
	 * @param List<Object>
	 * @return
	 */
	public List<Object> findO(String hql, List<Object> param);
	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<T> find(String hql, List<Object> param);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param 尝试是 Object 集合
	 * @param page  查询第几页
	 * @param rows  每页显示几条记录
	 * @return
	 */
	public List<T> find(String hql, Object[] param, Integer page, Integer rows);
	
	

	/**
	 * 查询集合(带分页)T
	 * 
	 * @param hql
	 * @param param 参数是List<Object>
	 * @param page  查询第几页
	 * @param rows  每页显示几条记录
	 * @return
	 */
	public List<T> find(String hql, List<Object> param, Integer page, Integer rows);
	
	/**
	 * 查询集合(带分页)Object
	 * 
	 * @param hql
	 * @param param 参数是List<Object>
	 * @param page  查询第几页
	 * @param rows  每页显示几条记录
	 * @return   返回的是对象
	 */
	public List<Object> findO(String hql, List<Object> param, Integer page, Integer rows);


	
	
	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 */
	public T get(String hql, Object[] param);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public T get(String hql, List<Object> param);

	/**
	 * 根据传进来的表名 查询对应的总行数
	 * @param hql
	 * @return
	 */
	public int countO(String tableName);
	
	/**
	 * 根据hql 查询分页总数
	 * @param hql
	 * @param param 参数
	 * @return
	 */
	public int countHql(String hql,List<Object> param);
	
	/**
	 * 统计当前继承类总数
	 * @param hql
	 * @return
	 */
	public int count();

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, Object[] param);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, List<Object> param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer executeHql(String hql);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @param param
	 * @return 响应数目
	 */
	public Integer executeHql(String hql, Object[] param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Integer executeHql(String hql, List<Object> param);
	
	/**
	 * 执行sql语句 查询出List<String>集合
	 * @param sql
	 * @return
	 */
	public List<String> queryBySqlStrList(String sql) ;

	/**
	 * 执行sql语句 查询出List<T>集合
	 * @param sql
	 * @return
	 */
	public List<T> queryBySqlT(String sql);
	


}
