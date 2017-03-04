package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.TableRowPosts;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import java.util.ArrayList;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServicePosts {
    ArrayList<TableRowPosts> getPostsByGroupID(String group_uuid) throws serviceException;
}
