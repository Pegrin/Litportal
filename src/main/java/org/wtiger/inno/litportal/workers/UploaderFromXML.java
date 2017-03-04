package org.wtiger.inno.litportal.workers;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbTable;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class UploaderFromXML<TR extends TableRow> implements Runnable {
    private static Logger logger = Logger.getLogger(UploaderFromXML.class);
    private final Table<TR> table;
    DBJaxbTable dbJaxbTable;
    private String filePath;

    public UploaderFromXML(Table<TR> table, String filePath) {
        this.table = table;
        this.filePath = filePath;
        dbJaxbTable = null;
    }

    public UploaderFromXML(Table<TR> table, String filePath, DBJaxbTable dbJaxbTable) {
        this.table = table;
        this.filePath = filePath;
        this.dbJaxbTable = dbJaxbTable;
    }

    private static Table fromXmlToObject(String filePath, Class theClass) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(theClass);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Table) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Integer i = 0;
        System.out.println(i.intValue());
        return null;
    }

    @Override
    public void run() {
        if (table.isReady()) {
            table.setReady(false);
            Table<TR> tmpTable = fromXmlToObject(filePath, table.getClass());
            table.setListOfRows(tmpTable.getListOfRows());
            table.setReady(true);
            if (dbJaxbTable != null) {
                (new LoaderToBase<TR, DBJaxbTable<Table<TR>, TR>>(table, dbJaxbTable)).run();
            }
        } else {
            logger.error("Ошибка в ходе загрузки данных в базу. Таблица не была разблокирована.");
        }

    }
}
