package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.TableRowGroups;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import java.util.ArrayList;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceGroups {
    TableRowGroups getObjectById(String group_uuid) throws serviceException;

    ArrayList<TableRowGroups> getListOfGroupsByParentID(String group_uuid) throws serviceException;
}
