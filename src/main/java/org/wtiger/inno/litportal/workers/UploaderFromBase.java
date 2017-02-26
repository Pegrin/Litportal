package org.wtiger.inno.litportal.workers;

import org.wtiger.inno.litportal.dbtools.DBTable;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.TTable;

import java.sql.SQLException;

/**
 * Created by olymp on 22.02.2017.
 */
public class UploaderFromBase<TR extends TableRow, DBT extends DBTable> implements Runnable {
    private final TTable<TR> table;
    private final DBT dbTable;
    private String fileName;

    public UploaderFromBase(TTable<TR> table, DBT dbTable) {
        this.table = table;
        this.dbTable = dbTable;
        this.fileName = null;
    }

    public UploaderFromBase(TTable<TR> table, DBT dbTable, String fileName) {
        this.table = table;
        this.dbTable = dbTable;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        table.setReady(false);
        try {
            dbTable.loadObjsFromDB(table);
            dbTable.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            table.setReady(true);
        }
        if (fileName != null)
            (new LoaderToXML(table, fileName)).run();
    }
}