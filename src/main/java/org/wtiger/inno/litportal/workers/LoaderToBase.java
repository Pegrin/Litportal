package org.wtiger.inno.litportal.workers;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbTable;
import org.wtiger.inno.litportal.models.hibernate.TableRow;
import org.wtiger.inno.litportal.models.tables.Table;

import java.sql.SQLException;

public class LoaderToBase<TR extends TableRow, DBT extends DBJaxbTable> implements Runnable {
    private static Logger logger = Logger.getLogger(LoaderToBase.class);
    private final Table<TR> table;
    private final DBT dbTable;

    public LoaderToBase(Table<TR> table, DBT dbTable) {
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
                logger.error("Ошибка в ходе загрузки таблицы в базу.", e);
            } finally {
                dbTable.close();
            }
            table.setReady(true);
        } else {
            logger.error("Ошибка в ходе загрузки данных в базу. Таблица не была разблокирована.");
        }
    }
}
