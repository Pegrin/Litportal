package org.wtiger.inno.litportal.dbtools.hibernate.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOComments;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.dbtools.hibernate.SingletonEntityManagerFactory;
import org.wtiger.inno.litportal.models.rows.CommentsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 05.03.2017.
 */
@Repository
public class HibernateComments implements DAOComments<CommentsEntity, UUID, UUID, UUID> {
    private Logger logger = Logger.getLogger(HibernateComments.class);
    private EntityManagerFactory emf;

    public HibernateComments() {
        emf = SingletonEntityManagerFactory.getInstance();
    }

    @Override
    public CommentsEntity getNewEntity(UUID postUuid, UUID parentCommentUuid, String body, UUID userUuid) {
        CommentsEntity comment = new CommentsEntity();
        comment.setPostUuid(postUuid);
        comment.setParentCommentUuid(parentCommentUuid);
        comment.setBody(body);
        comment.setUserUuid(userUuid);
        return comment;
    }

    @Override
    public void deleteAll() throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CommentsEntity").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void persist(CommentsEntity commentsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(commentsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<CommentsEntity> findAll() throws DBException {
        EntityManager em = emf.createEntityManager();
        List<CommentsEntity> resultList = em.createQuery("from CommentsEntity t", CommentsEntity.class).getResultList();
        em.close();
        return resultList;
    }

    @Override
    public CommentsEntity findByID(UUID uuid) throws DBException {
        EntityManager em = emf.createEntityManager();
        CommentsEntity comment = em.find(CommentsEntity.class, uuid);
        em.close();
        return comment;
    }

    @Override
    public List<CommentsEntity> findByPostID(UUID id) throws DBException {
        EntityManager em = emf.createEntityManager();
        List<CommentsEntity> resultList = em.createQuery("select t from CommentsEntity as t where t.postUuid = :id"
                , CommentsEntity.class).setParameter("id", id).getResultList();
        em.close();
        return resultList;
    }

    @Override
    public List<CommentsEntity> findByUserID(UUID id) throws DBException {
        EntityManager em = emf.createEntityManager();
        List<CommentsEntity> resultList = em.createQuery("select t from CommentsEntity as t where t.userUuid = :id"
                , CommentsEntity.class).setParameter("id", id).getResultList();
        em.close();
        return resultList;
    }

    @Override
    public void update(CommentsEntity commentsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(commentsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(CommentsEntity commentsEntity) throws DBException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(commentsEntity);
        em.getTransaction().commit();
        em.close();
    }
}
