package h2o.dao.advanced;

import h2o.common.util.collections.CollectionUtil;

import java.util.List;

public class BasicRepository<E> {

    public void add(E entity) {
        this.createDaoBasicUtil(entity).add(entity);
    }

    public void batAdd(List<E> entities) {
        if (CollectionUtil.isNotBlank(entities)) {
            this.createDaoBasicUtil(entities.get(0)).batAdd(entities);
        }
    }

    public int edit(E entity) {
        return this.createDaoBasicUtil(entity).edit(entity);
    }

    public int editByUnique(E entity, String uniqueName) {
        return this.createDaoBasicUtil(entity).editByUnique(entity, uniqueName);
    }

    public int editByAttrNames(E entity, String[] attrNames) {
        return this.createDaoBasicUtil(entity).editByAttrNames(entity, attrNames);
    }

    public E get(E entity) {
        return this.createDaoBasicUtil(entity).get(entity);
    }

    public E getAndLock(E entity) {
        return this.createDaoBasicUtil(entity).getAndLock(entity);
    }

    public E get(E entity, boolean lock) {
        return this.createDaoBasicUtil(entity).get(entity, lock);
    }

    public E getByUnique(E entity, String uniqueName, boolean lock) {
        return this.createDaoBasicUtil(entity).getByUnique(entity, uniqueName, lock);
    }

    public E getByAttrNames(E entity, String[] attrNames, boolean lock) {
        return this.createDaoBasicUtil(entity).getByAttrNames(entity, attrNames, lock);
    }

    public int del(E entity) {
        return this.createDaoBasicUtil(entity).del(entity);
    }

    public int delByUnique(E entity, String uniqueName) {
        return this.createDaoBasicUtil(entity).delByUnique(entity, uniqueName);
    }

    public int delByAttrNames(E entity, String[] attrNames) {
        return this.createDaoBasicUtil(entity).delByAttrNames(entity, attrNames);
    }

    protected DaoBasicUtil<E> createDaoBasicUtil( E entity ) {
        return new DaoBasicUtil<E>(entity.getClass());
    }

}
