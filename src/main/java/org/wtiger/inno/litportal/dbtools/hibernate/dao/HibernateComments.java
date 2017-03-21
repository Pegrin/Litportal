package org.wtiger.inno.litportal.dbtools.hibernate.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOComments;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.dbtools.hibernate.SingletonEntityManagerFactory;
import org.wtiger.inno.litportal.models.hibernate.CommentsEntity;

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
        CommentsEntity comment = new CommentsEntity(null, postUuid, parentCommentUuid, body, null, userUuid);
        return comment;
    }

    @Override
    public void deleteAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM CommentsEntity ").executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении всех элементов таблицы", e);
            throw new DBException();
        }
    }

    @Override
    public void persist(CommentsEntity commentsEntity) throws DBException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(commentsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при сохранении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public List<CommentsEntity> findAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<CommentsEntity> resultList = em.createQuery("from CommentsEntity t", CommentsEntity.class).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при писка элементов", e);
            throw new DBException();
        }
    }

    @Override
    public CommentsEntity findByID(UUID uuid) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            CommentsEntity comment = null;
            try {
                comment = em.find(CommentsEntity.class, uuid);
            } catch (Exception e) {
                logger.error("Ошибка работы с базой данных при получении элемента", e);
                throw new DBException();
            }
            em.close();
            return comment;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при работе с менеджером", e);
            throw new DBException();
        }
    }

    @Override
    public List<CommentsEntity> findByPostID(UUID id) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<CommentsEntity> resultList = em.createQuery("select t from CommentsEntity as t where t.postUuid = :id"
                    , CommentsEntity.class).setParameter("id", id).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении списка элементов", e);
            throw new DBException();
        }
    }

    @Override
    public List<CommentsEntity> findByUserID(UUID id) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<CommentsEntity> resultList = em.createQuery("select t from CommentsEntity as t where t.userUuid = :id"
                    , CommentsEntity.class).setParameter("id", id).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении списка элементов.", e);
            throw new DBException();
        }
    }

    @Override
    public void update(CommentsEntity commentsEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(commentsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при обновлении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public void delete(CommentsEntity commentsEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            commentsEntity = em.find(CommentsEntity.class, commentsEntity.getCommentUuid());
            em.remove(commentsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении элемента", e);
            throw new DBException();
        }
    }
}
