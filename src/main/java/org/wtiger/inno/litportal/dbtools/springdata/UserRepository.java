package org.wtiger.inno.litportal.dbtools.springdata;

import org.springframework.data.repository.CrudRepository;
import org.wtiger.inno.litportal.models.hibernate.UsersEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 21.03.2017.
 */
public interface UserRepository extends CrudRepository<UsersEntity, UUID> {
    UsersEntity findByLoginIgnoreCase(String login);

    List<UsersEntity> findAllOrderByLogin();
}
