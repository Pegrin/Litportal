package org.wtiger.inno.litportal.models.tables;

import java.util.ArrayDeque;

/**
 * Created by olymp on 21.02.2017.
 */
public interface TTable<TR> {

    boolean isReady();

    void setReady(boolean ready);

    ArrayDeque<TR> getListOfRows();

    void setListOfRows(ArrayDeque<TR> listOfRows);
}
