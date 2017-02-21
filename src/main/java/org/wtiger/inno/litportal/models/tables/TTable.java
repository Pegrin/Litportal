package org.wtiger.inno.litportal.models.tables;

import java.util.ArrayList;

/**
 * Created by olymp on 21.02.2017.
 */
public interface TTable<TR> {

    ArrayList<TR> getListOfRows();

    void setListOfRows(ArrayList<TR> listOfRows);
}
