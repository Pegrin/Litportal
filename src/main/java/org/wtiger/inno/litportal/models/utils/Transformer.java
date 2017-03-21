package org.wtiger.inno.litportal.models.utils;

import org.wtiger.inno.litportal.models.hibernate.TableRow;
import org.wtiger.inno.litportal.models.pojo.Pojo;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 16.03.2017.
 */
public interface Transformer<E extends TableRow, P extends Pojo> {
    E transformFromPojoToEntity(P p);

    P transformFromEntityToPojo(E e);
}
