package org.wtiger.inno.litportal.dbtools.hibernate.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOGroups;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.dbtools.hibernate.SingletonEntityManagerFactory;
import org.wtiger.inno.litportal.models.rows.GroupsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Repository
public class HibernateGroups implements DAOGroups<GroupsEntity, UUID> {
    private Logger logger = Logger.getLogger(HibernateComments.class);
    private EntityManagerFactory emf;

    public HibernateGroups() {
        emf = SingletonEntityManagerFactory.getInstance();
    }

    @Override
    public GroupsEntity getNewEntity(UUID parent_group_uuid, String head, String body) {
        GroupsEntity group = new GroupsEntity();
        group.setParentGroupUuid(parent_group_uuid);
        group.setHead(head);
        group.setBody(body);
        return group;
    }

    @Override
    public List<GroupsEntity> findByParentID(UUID id) throws DBException {
        EntityManager em = emf.createEntityManager();
        List<GroupsEntity> resultList;
        if (id != null) {
            resultList = em.createQuery("select t from GroupsEntity as t where t.parentGroupUuid = :id"
                    , GroupsEntity.class).setParameter("id", id).getResultList();
        } else {
            resultList = em.createQuery("select t from GroupsEntity as t where t.parentGroupUuid is null "
                    , GroupsEntity.class).getResultList();
        }
        em.close();
        return resultList;
    }

    @Override
    public void deleteAll() throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM GroupsEntity ").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void persist(GroupsEntity groupsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(groupsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<GroupsEntity> findAll() throws DBException {
        EntityManager em = emf.createEntityManager();
        List<GroupsEntity> resultList = em.createQuery("from GroupsEntity t", GroupsEntity.class).getResultList();
        em.close();
        return resultList;
    }

    @Override
    public GroupsEntity findByID(UUID uuid) throws DBException {
        EntityManager em = emf.createEntityManager();
        GroupsEntity group = em.find(GroupsEntity.class, uuid);
        em.close();
        return group;
    }

    @Override
    public void update(GroupsEntity groupsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(groupsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(GroupsEntity groupsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(groupsEntity);
        em.getTransaction().commit();
        em.close();
    }
}
