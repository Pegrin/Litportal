package org.wtiger.inno.litportal.dbtools.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.hibernate.UsersEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 21.03.2017.
 */
@Repository
public class SpringUserDAO implements DAOUsers<UsersEntity, UUID> {
    private UserRepository userRepository;

    @Override
    public UsersEntity getNewEntity(String login, String password, short role, String email, String visibleName) {
        UsersEntity user = new UsersEntity(null, login, password, role,
                email, visibleName, true);
        return user;
    }

    @Override
    public UsersEntity getByLogin(String login) throws DBException {
        return userRepository.findByLoginIgnoreCase(login);
    }

    @Override
    public void deleteAll() throws DBException {
        userRepository.deleteAll();
    }

    @Override
    public void persist(UsersEntity usersEntity) throws DBException {
        userRepository.save(usersEntity);
    }

    @Override
    public List<UsersEntity> findAll() throws DBException {
        return userRepository.findAll();
    }

    @Override
    public UsersEntity findByID(UUID uuid) throws DBException {
        return userRepository.findOne(uuid);
    }

    @Override
    public void update(UsersEntity usersEntity) throws DBException {
        userRepository.save(usersEntity);
    }

    @Override
    public void delete(UsersEntity usersEntity) throws DBException {
        userRepository.delete(usersEntity);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
