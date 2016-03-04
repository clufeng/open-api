package com.yonyou.mcloud.dao;

import com.yonyou.mcloud.model.IBaseVO;
import com.yonyou.mcloud.pojo.Page;
import com.yonyou.mcloud.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用DAO实现
 * Created by hubo on 2016/2/27
 */
public class GenericDaoImpl<T extends IBaseVO, PK extends Serializable> implements GenericDao<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        this.entityClass = ReflectUtils.findParameterizedType(getClass());
    }

    public T get(final PK id) {
        return hibernateTemplate.get(entityClass, id);
    }

    public T load(final PK id) {
        return hibernateTemplate.load(entityClass, id);
    }

    public void evict(T entity) {
        hibernateTemplate.evict(entity);
    }

    @SuppressWarnings("unchecked")
    public PK save(T entity) {
        Assert.notNull(entity);
        PK pk = (PK) hibernateTemplate.save(entity);
        logger.debug("save entity: {}", entity);
        return pk;
    }

    public void update(T entity) {
        Assert.notNull(entity);
        hibernateTemplate.update(entity);
        logger.debug("update entity: {}", entity);
    }

    public void saveOrUpdate(T entity) {
        Assert.notNull(entity);
        hibernateTemplate.saveOrUpdate(entity);
        logger.debug("save or update entity: {}", entity);
    }

    public void updateSelective(T entity) {
        Assert.notNull(entity);
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> params = new ArrayList<>();
        final List<Object> values = new ArrayList<>();
        for(Field field : fields) {
            String fieldName = field.getName();
            if(fieldName.equals("id")) {
                continue;
            }
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, entity);
            if(value != null) {
                params.add(fieldName);
                values.add(value);
            }
        }
        if(!params.isEmpty()) {
            final StringBuilder sqlBuilder = new StringBuilder("update "
                    + entityClass.getName() + " set ");
            for(int i = 0; i < params.size(); i++) {
                sqlBuilder.append(params.get(i)).append(" = ? ");
                if(i < params.size() - 1) {
                    sqlBuilder.append(", ");
                }
            }
            Field pkField = ReflectionUtils.findField(entityClass, "id");

            Assert.notNull(pkField);
            pkField.setAccessible(true);
            sqlBuilder.append(" where id = ? ");
            values.add(ReflectionUtils.getField(pkField, entity));

            hibernateTemplate.executeWithNativeSession(new HibernateCallback<Void>() {
                @Override
                public Void doInHibernate(Session session) throws HibernateException {
                    createQuery(session, sqlBuilder.toString(), values.toArray()).executeUpdate();
                    return null;
                }
            });

            logger.info("update entity selecitive: {}" + entity);
        }
    }

    @SuppressWarnings("unchecked")
    public T merge(T entity) {
        Assert.notNull(entity);
        T t = hibernateTemplate.merge(entity);
        logger.info("merge entity: {}", entity);
        return t;
    }

    public void delete(T entity) {
        Assert.notNull(entity);
        hibernateTemplate.delete(entity);
        logger.info("delete entity: {}", entity);
    }

    public void delete(PK id) {
        Assert.notNull(id);
        delete(get(id));
    }

    public List<T> findAll() {
        return findByCriteria(Restrictions.eq(IBaseVO.DR_FEILD, IBaseVO.DR_NORMAL));
    }

    public Page<T> findAll(Page<T> page) {
        return findByCriteria(page, Restrictions.eq(IBaseVO.DR_FEILD, IBaseVO.DR_NORMAL));
    }

    @SuppressWarnings("unchecked")
    public List<T> find(final String hql, final Object... values) {
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                return (List<T>)createQuery(session, hql, values).list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findBySql(final String sql, final Object... values) {
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                return (List<T>)createSQLQuery(session, sql, values).addEntity(entityClass).list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Page<T> find(final Page<T> page, final String hql, final Object... values) {
        Assert.notNull(page);

        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Page<T>>() {
            @Override
            public Page<T> doInHibernate(Session session) throws HibernateException {
                if(page.isAutoCount()) {
                    page.setTotalCount(countQueryResult(hql, values));
                }
                Query q = createQuery(session, hql, values);
                if(page.isFirstSetted()) {
                    q.setFirstResult(page.getFirst());
                }
                if(page.isPageSizeSetted()) {
                    q.setMaxResults(page.getPageSize());
                }
                page.setResult((List<T>)q.list());
                return page;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Page<T> findBySql(final Page<T> page, final String sql, final Object... values) {
        Assert.notNull(page);

        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Page<T>>() {
            @Override
            public Page<T> doInHibernate(Session session) throws HibernateException {
                if(page.isAutoCount()) {
                    page.setTotalCount(countSQLQueryResult(sql, values));
                }
                SQLQuery q = createSQLQuery(session, sql, values);
                if(page.isFirstSetted()) {
                    q.setFirstResult(page.getFirst());
                }
                if(page.isPageSizeSetted()) {
                    q.setMaxResults(page.getPageSize());
                }
                page.setResult((List<T>)q.addEntity(entityClass).list());
                return page;
            }
        });
    }

    public Object findUnique(final String hql, final Object... values) {
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return createQuery(session, hql, values).uniqueResult();
            }
        });
    }

    public Object findUniqueBySql(final String sql, final Object... values) {
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return createSQLQuery(session, sql, values).addEntity(entityClass).uniqueResult();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(final Criterion... criterion) {
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                return (List<T>)createCriteria(session, criterion).list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Page<T> findByCriteria(final Page<T> page, final Criterion... criterion) {
        Assert.notNull(page);

        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Page<T>>() {
            @Override
            public Page<T> doInHibernate(Session session) throws HibernateException {
                Criteria c = createCriteria(session, criterion);
                if(page.isAutoCount()) {
                    page.setTotalCount(countCriteriaResult(page, c));
                }
                if(page.isFirstSetted()) {
                    c.setFirstResult(page.getFirst());
                }
                if(page.isPageSizeSetted()) {
                    c.setMaxResults(page.getPageSize());
                }
                if(page.isOrderBySetted()) {
                    if(page.getOrder().toUpperCase().equals("AES")) {
                        c.addOrder(Order.asc(page.getOrderBy()));
                    } else {
                        c.addOrder(Order.desc(page.getOrderBy()));
                    }
                }
                page.setResult((List<T>)c.list());
                return page;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                return (List<T>) createCriteria(session, Restrictions.eq(propertyName, value)).list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public T findUniqueByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        return hibernateTemplate.executeWithNativeSession(new HibernateCallback<T>() {
            @Override
            public T doInHibernate(Session session) throws HibernateException {
                return (T)createCriteria(session, Restrictions.eq(propertyName, value)).uniqueResult();
            }
        });
    }

    public boolean isPropertyUnique(String propertyName, Object newValue) {
        if(newValue == null) return false;
        try {
            Object obj = findUniqueByProperty(propertyName, newValue);
            return obj == null;
        } catch (HibernateException e) {
            return false;
        }
    }

    public Query createQuery(Session session, String queryString, Object... values) {
        Assert.hasText(queryString);
        Query queryObject = session.createQuery(queryString);
        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    public SQLQuery createSQLQuery(Session session, String queryString, Object... values) {
        Assert.hasText(queryString);
        SQLQuery queryObject = session.createSQLQuery(queryString);
        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    public Criteria createCriteria(Session sessioin, Criterion... criterions) {
        Criteria criteria = sessioin.createCriteria(entityClass);
        for(Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public long countQueryResult(String hql, final Object... values) {
        hql = hql.replaceAll(" [Ff][Rr][Oo][Mm] ", " from ");
        String str = hql.toLowerCase();
        if(!StringUtils.contains(str, "group by")
                && !StringUtils.contains(str, "union")
                && !StringUtils.contains(str, "minus")
                && !StringUtils.contains(str, "intersect")
                && !StringUtils.contains(StringUtils.substringAfter(str, "from"), "(")
                ) {
            str = "select count(1) from " + StringUtils.substringAfter(hql, "from");

            final String countHql = str;
            return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
                @Override
                public Long doInHibernate(Session session) throws HibernateException {
                    return ((Number)createQuery(session, countHql, values).iterate().next()).longValue();
                }
            });
        } else {
            throw new HibernateException("not support this HQL : " + hql);
        }
    }

    public long countSQLQueryResult(String sql, final Object... values) {
        String str = sql.toLowerCase();
        String beforeFrom = StringUtils.substringBefore(str, "from");
        if(StringUtils.countMatches(beforeFrom, "(")
                != StringUtils.countMatches(beforeFrom, ")")
                || StringUtils.contains(str, "group by")
                || StringUtils.contains(str, "union")
                || StringUtils.contains(str, "minus")
                || StringUtils.contains(str, "intersect")) {
            str = "select count(1) from (" + sql + ") as tmp";
        } else {
            str = "select count(1) from " +
                    StringUtils.substringAfter(str, "from");
        }

        final String countSql = str;

        Object ret = hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return createSQLQuery(session, countSql, values).uniqueResult();
            }
        });

        return (ret == null ? 0 : ((Number)ret).longValue());
    }

    @SuppressWarnings("unchecked")
    public long countCriteriaResult(Page<T> page, Criteria c) {
        CriteriaImpl cimpl = (CriteriaImpl)c;

        // First Projection, ResultTransformer, OrderBy out ,Empty after a three Count operations
        Projection projection = cimpl.getProjection();
        ResultTransformer transformer = cimpl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            Field orderEntriesField = cimpl.getClass().getDeclaredField("orderEntries");
            orderEntriesField.setAccessible(true);
            orderEntries = (List<CriteriaImpl.OrderEntry>) ReflectionUtils.getField(
                    orderEntriesField, cimpl);
            ReflectionUtils.setField(
                    orderEntriesField, cimpl,
                    new ArrayList<CriteriaImpl.OrderEntry>());
        } catch (Exception e) {
            logger.error("Not may throw an exception :{}", e.getMessage());
        }

        // Do Count query
        long totalCount = (Long) c.setProjection(Projections.rowCount())
                .uniqueResult();
        if (totalCount < 1)
            return -1;

        // Will the Projection and OrderBy before conditions back to go back
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }

        try {
            Field orderEntriesField = cimpl.getClass().getDeclaredField("orderEntries");
            orderEntriesField.setAccessible(true);
            ReflectionUtils.setField(orderEntriesField, cimpl, orderEntries);
        } catch (Exception e) {
            logger.error("Not may throw an exception :{}", e.getMessage());
        }

        return totalCount;

    }

    private int tuneBatchSize(int batchSize) {
        if(batchSize < 20) {
            batchSize = 20;
        } else if(batchSize > 200) {
            batchSize = 200;
        }
        return batchSize;
    }

    public int batchSave(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            session.save(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

    public int batchUpdate(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            session.update(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

    public int batchSaveOrUpdate(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            session.saveOrUpdate(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

    public int batchUpdateSelective(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            updateSelective(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

    public int batchMerge(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            session.merge(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

    public int batchDelete(List<T> entities, int batchSize) {
        Assert.notEmpty(entities);
        batchSize = tuneBatchSize(batchSize);
        int count = 0;
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            session.delete(entities.get(i));
            if(i % batchSize == 0 || i == entities.size() - 1) {
                session.flush();
                session.clear();
            }
            count++;
        }
        transaction.commit();
        SessionFactoryUtils.closeSession(session);
        return count;
    }

}
