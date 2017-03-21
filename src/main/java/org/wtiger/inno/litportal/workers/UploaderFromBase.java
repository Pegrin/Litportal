package org.wtiger.inno.litportal.workers;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbTable;
import org.wtiger.inno.litportal.models.hibernate.TableRow;
import org.wtiger.inno.litportal.models.tables.Table;

import java.sql.SQLException;

/**
 * Created by olymp on 22.02.2017.
 */
public class UploaderFromBase<TR extends TableRow, DBT extends DBJaxbTable> implements Runnable {
    private static Logger logger = Logger.getLogger(UploaderFromBase.class);

    private final Table<TR> table;
    private final DBT dbTable;
    private String fileName;

    public UploaderFromBase(Table<TR> table, DBT dbTable) {
        this.table = table;
        this.dbTable = dbTable;
        this.fileName = null;
    }

    public UploaderFromBase(Table<TR> table, DBT dbTable, String fileName) {
        this.table = table;
        this.dbTable = dbTable;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        table.setReady(false);
        try {
            dbTable.loadObjsFromDB(table);
            dbTable.close();
        } catch (SQLException e) {
            logger.error("Не удалось получить таблицу из базы.", e);
        } finally {
            table.setReady(true);
        }
        if (fileName != null)
            (new LoaderToXML(table, fileName)).run();
    }
}