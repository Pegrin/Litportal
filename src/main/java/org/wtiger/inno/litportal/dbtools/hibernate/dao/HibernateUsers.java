package org.wtiger.inno.litportal.dbtools.hibernate.dao;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.dbtools.hibernate.SingletonEntityManagerFactory;
import org.wtiger.inno.litportal.models.hibernate.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 05.03.2017.
 */
//@Repository
public class HibernateUsers implements DAOUsers<UsersEntity, UUID> {
    private Logger logger = Logger.getLogger(HibernateComments.class);
    private EntityManagerFactory emf;

    public HibernateUsers() {
        emf = SingletonEntityManagerFactory.getInstance();
    }

    @Override
    public UsersEntity getNewEntity(String login, String password, short role, String email, String visibleName) {
        UsersEntity user = new UsersEntity(null, login, password, role,
                email, visibleName, true);
        return user;
    }

    @Override
    public UsersEntity getByLogin(String login) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            UsersEntity user;
            try {
                user = em.createQuery("from UsersEntity as t where t.login = :login", UsersEntity.class)
                        .setParameter("login", login).getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                user = null;
            } catch (Exception e) {
                String msg = "Ошибка при получении пользователя по логину.";
                logger.error(msg, e);
                throw new DBException();
            }
            em.close();
            return user;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении пользователя по логину", e);
            throw new DBException();
        }
    }

    @Override
    public void deleteAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM UsersEntity ").executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении всех пользователей из таблицы.", e);
            throw new DBException();
        }
    }

    @Override
    public void persist(UsersEntity usersEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(usersEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при сохранении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public List<UsersEntity> findAll() throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            List<UsersEntity> resultList = em.createQuery("from UsersEntity t", UsersEntity.class).getResultList();
            em.close();
            return resultList;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении списка всех элементов в таблице", e);
            throw new DBException();
        }
    }

    @Override
    public UsersEntity findByID(UUID uuid) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            UsersEntity user = em.find(UsersEntity.class, uuid);
            em.close();
            return user;
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при получении элемента по ID", e);
            throw new DBException();
        }
    }

    @Override
    public void update(UsersEntity usersEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(usersEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при обновлении элемента", e);
            throw new DBException();
        }
    }

    @Override
    public void delete(UsersEntity usersEntity) throws DBException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(usersEntity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error("Ошибка работы с базой данных при удалении элемента", e);
            throw new DBException();
        }
    }
}
