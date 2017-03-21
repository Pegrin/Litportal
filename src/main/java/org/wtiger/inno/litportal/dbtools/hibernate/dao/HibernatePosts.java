package org.wtiger.inno.litportal.dbtools.hibernate.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOPosts;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.dbtools.hibernate.SingletonEntityManagerFactory;
import org.wtiger.inno.litportal.models.hibernate.PostsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 05.03.2017.
 */
@Repository
public class HibernatePosts implements DAOPosts<PostsEntity, UUID, UUID, UUID> {
    private Logger logger = Logger.getLogger(HibernateComments.class);
    private EntityManagerFactory emf;

    public HibernatePosts() {
        emf = SingletonEntityManagerFactory.getInstance();
    }

    @Override
    public PostsEntity getNewEntity(UUID groupUuid, Timestamp date, String head, String newBodyRequest, UUID userUuid) {
        PostsEntity post = new PostsEntity(null, groupUuid, date,
                head, null, newBodyRequest, null, userUuid);
        return post;
    }

    @Override
    public void deleteAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PostsEntity ").executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении всех элементов таблицы", e);
            throw new DBException();
        }
    }

    @Override
    public void persist(PostsEntity postsEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(postsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при сохранении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public List<PostsEntity> findAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<PostsEntity> resultList = em.createQuery("from PostsEntity t", PostsEntity.class).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении всех элементов таблицы", e);
            throw new DBException();
        }
    }

    @Override
    public PostsEntity findByID(UUID uuid) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            PostsEntity post = em.find(PostsEntity.class, uuid);
            em.close();
            return post;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public List<PostsEntity> findByGroupID(UUID id) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<PostsEntity> resultList;
            if (id != null) {
                resultList = em.createQuery("select t from PostsEntity as t where t.groupUuid = :id", PostsEntity.class)
                        .setParameter("id", id).getResultList();
            } else {
                resultList = em.createQuery("select t from PostsEntity as t where t.groupUuid is null "
                        , PostsEntity.class).getResultList();
            }
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении списка элементов", e);
            throw new DBException();
        }
    }

    @Override
    public List<PostsEntity> findByUserID(UUID id) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<PostsEntity> resultList = em.createQuery("select t from PostsEntity as t where t.userUuid = :id", PostsEntity.class)
                    .setParameter("id", id).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении списка элементов", e);
            throw new DBException();
        }
    }

    @Override
    public void update(PostsEntity postsEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(postsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при обновлении элемента.", e);
            throw new DBException();
        }
    }

    @Override
    public void delete(PostsEntity postsEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(postsEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении элемента", e);
            throw new DBException();
        }
    }
}
