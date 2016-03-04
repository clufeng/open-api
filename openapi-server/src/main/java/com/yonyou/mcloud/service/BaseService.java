package com.yonyou.mcloud.service;

import com.yonyou.mcloud.dao.GenericDao;
import com.yonyou.mcloud.exception.BusinessException;
import com.yonyou.mcloud.model.IBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 基础服务
 * Created by hubo on 2016/2/29
 */
public class BaseService<T extends IBaseVO> {

    protected Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    private GenericDao<T, String> dao;

    public List<T> selectAll(){
        return dao.findAll();
    }

    public T get(String id){
        return dao.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(T entity) throws BusinessException {

        if(entity == null) {
            throw new NullPointerException("entity is null!");
        }

        entity.setDr(IBaseVO.DR_NORMAL);
        setNewTs(entity);

        try {
            dao.save(entity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void update(T entity) throws BusinessException{
        validateTs(entity);
        setNewTs(entity);
        try {
            dao.update(entity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void merge(T entity) throws BusinessException{
        validateTs(entity);
        setNewTs(entity);
        try {
            dao.merge(entity);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public void logicDelete(T entity) throws BusinessException{

        T newEntity;

        try {

            validateTs(entity);
            newEntity = (T)entity.getClass().newInstance();
            newEntity.setId(entity.getId());
            newEntity.setDr(IBaseVO.DR_DEL);
            setNewTs(newEntity);
            dao.updateSelective(newEntity);

        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    private void validateTs(T entity) throws BusinessException{

        if(entity == null || entity.getTs() == null) {
            return;
        }

        T origEntity = get(entity.getId());

        if(entity.getTs().compareTo(origEntity.getTs()) != 0) {
            throw new BusinessException("F0001");
        }

    }

    protected void setNewTs(T entity) {
        entity.setTs(new Date().getTime());
    }



}
