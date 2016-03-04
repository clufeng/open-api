package com.yonyou.mcloud.dao;

import com.yonyou.mcloud.model.IBaseVO;
import com.yonyou.mcloud.pojo.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hubo on 2016/2/27
 */
public interface GenericDao<T extends IBaseVO, PK extends Serializable> {

    /**
     * Get entity by primary key
     *
     * @param id Primary key
     * @return Entity object
     */
    public T get(final PK id);

    /**
     * Load entity by primary key
     *
     * @param id Primary key
     * @return Entity object
     */
    public T load(final PK id);

    /**
     * Remove entity from session, changes to this entity will
     * not be synchronized with the database
     *
     * @param entity The object to be evicted
     */
    public void evict(T entity);

    /**
     * Save entity
     *
     * @param entity The object to be saved
     * @return The generated identifier
     */
    public PK save(T entity);


    /**
     * Update entity
     *
     * @param entity The object to be updated
     */
    public void update(T entity);

    /**
     * Save or update entity
     *
     * @param entity The object to be save or update
     */
    public void saveOrUpdate(T entity);

    /**
     * Update entity's not-null property
     * (this entity must have a primary key property "id")
     *
     * @param entity The object to be updated
     */
    public void updateSelective(T entity);

    /**
     * Merge entity
     *
     * @param entity The object to be merged into database
     * @return The persistent object
     */
    public T merge(T entity);

    /**
     * Delete entity (actually, delete by primary key)
     *
     * @param entity The object to be deleted
     */
    public void delete(T entity);

    /**
     * Delete entity by primary key, first get then delete
     *
     * @param id Primary key
     */
    public void delete(PK id);

    /**
     * Find all entities
     *
     * @return Query result list
     */
    public List<T> findAll();

    /**
     * Find entities by page
     *
     * @param page Paging object
     * @return Paging query result, Comes with a results list
     */
    public Page<T> findAll(Page<T> page);

    /**
     * Press the HQL Query object list
     *
     * @param hql    HQL statement
     * @param values Number of variable parameters
     * @return Query result list
     */
    public List<T> find(String hql, Object... values);

    /**
     * Press the SQL Query object list
     *
     * @param sql    SQL statement
     * @param values Number of variable parameters
     * @return Query result list
     */
    public List<T> findBySql(String sql, Object... values);

    /**
     * Press the HQL query paging .
     *
     * @param page   Paging parameters .
     * @param hql    HQL statement .
     * @param values Number of variable parameters .
     * @return Paging query results ,Comes with a results list .
     */
    public Page<T> find(Page<T> page, String hql, Object... values);

    /**
     * Press the SQL query paging .
     *
     * @param page   Paging parameters .
     * @param sql    SQL statement .
     * @param values Number of variable parameters .
     * @return Paging query results ,Comes with a results list .
     */
    public Page<T> findBySql(Page<T> page, String sql, Object... values);

    /**
     * Press the HQL query only object
     *
     * @param hql    HQL statement
     * @param values Number of variable parameters
     * @return A single instance that matches the query, or null if the query returns no results
     */
    public Object findUnique(String hql, Object... values);

    /**
     * Press the SQL query only object
     *
     * @param sql    SQL statement
     * @param values Number of variable parameters
     * @return A single instance that matches the query, or null if the query returns no results
     */
    public Object findUniqueBySql(String sql, Object... values);

    /**
     * According to the Criterion query object list .
     *
     * @param criterion Number of variable Criterion .
     * @return Query result list
     */
    public List<T> findByCriteria(Criterion... criterion);

    /**
     * According to the Criterion paging query .
     *
     * @param page      Paging parameters .Including the pageSize, firstResult, orderBy, asc, autoCount .
     *                  Where firstResult can be directly specified ,You can also specify pageNo . autoCountSpecifies whether dynamic gets total number of results .
     * @param criterion Number of variable criterion .
     * @return Paging query results .Comes with a results list and all query parameters .
     */
    public Page<T> findByCriteria(Page<T> page, Criterion... criterion);

    /**
     * Find a list of objects by property .
     *
     * @param propertyName Property name of the entity
     * @param value        Property value
     * @return Query result list
     */
    public List<T> findByProperty(String propertyName, Object value);

    /**
     * Find unique object by property .
     *
     * @param propertyName Property name of the entity
     * @param value        Property value
     * @return A single instance that matches the query, or null if the query returns no
     */
    public T findUniqueByProperty(String propertyName, Object value);

    /**
     * Determine the object's property value is unique within the database .
     *
     * @param propertyName Property name of the entity
     * @param newValue     New property value
     */
    public boolean isPropertyUnique(String propertyName, Object newValue);

    /**
     * Count HQL query result
     *
     * @param hql    HQL statement
     * @param values Number of variable parameters
     * @return Result count
     */
    public long countQueryResult(String hql, Object... values);

    /**
     * Count SQL query result
     *
     * @param sql    HQL statement
     * @param values Number of variable parameters
     * @return Result count
     */
    public long countSQLQueryResult(String sql, Object... values);

    /**
     * Through this count query to obtain the total number of objects .
     *
     * @param page Paging object
     * @param c    Query criteria
     * @return The total number of objects of the query result.
     */
    public long countCriteriaResult(Page<T> page, Criteria c);

    /**
     * Save entities in batch
     *
     * @param entities  The objects to be saved
     * @param batchSize The number of every session flush
     * @return Successful save count
     */
    public int batchSave(List<T> entities, int batchSize);

    /**
     * Update entities in batch
     *
     * @param entities  The objects to be updated
     * @param batchSize The number of every session flush
     * @return Successful update count
     */
    public int batchUpdate(List<T> entities, int batchSize);

    /**
     * Save or update entities in batch
     *
     * @param entities  The objects to be saved or updated
     * @param batchSize The number of every session flush
     * @return Successful save count or update count
     */
    public int batchSaveOrUpdate(List<T> entities, int batchSize);

    /**
     * Update entities (not-null property) in batch
     *
     * @param entities  The objects to be updated
     * @param batchSize The number of every session flush
     * @return Successful update count
     */
    public int batchUpdateSelective(List<T> entities, int batchSize);

    /**
     * Merge entities in batch
     *
     * @param entities  The objects to be merged
     * @param batchSize The number of every session flush
     * @return Successful merge count
     */
    public int batchMerge(List<T> entities, int batchSize);

    /**
     * Delete entities in batch
     *
     * @param entities  The objects to be deleted
     * @param batchSize The number of every session flush
     * @return successful delete count
     */
    public int batchDelete(List<T> entities, int batchSize);

}