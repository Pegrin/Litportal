package org.wtiger.inno.litportal.workers;

import org.wtiger.inno.litportal.dbtools.DBTable;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.TTable;

import java.sql.SQLException;

/**
 * Created by olymp on 21.02.2017.
 */
public class LoaderToBase<TR extends TableRow, DBT extends DBTable> implements Runnable {
    private final TTable<TR> table;
    private final DBT dbTable;

    public LoaderToBase(TTable<TR> table, DBT dbTable) {
        this.table = table;
        this.dbTable = dbTable;
    }


    @Override
    public void run() {
        if (table.isReady()) {
            table.setReady(false);
            try {
                dbTable.loadObjsToDB(table);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbTable.close();
            }
            table.setReady(true);
        } else {
            //Ругаемся
        }
    }
}
