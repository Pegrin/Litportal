package org.wtiger.inno.litportal.workers;

import org.wtiger.inno.litportal.dbtools.DBTable;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.TTable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by olymp on 22.02.2017.
 */
public class UploaderFromXML<TR extends TableRow> implements Runnable {
    private final TTable<TR> table;
    DBTable dbTable;
    private String filePath;

    public UploaderFromXML(TTable<TR> table, String filePath) {
        this.table = table;
        this.filePath = filePath;
        dbTable = null;
    }

    public UploaderFromXML(TTable<TR> table, String filePath, DBTable dbTable) {
        this.table = table;
        this.filePath = filePath;
        this.dbTable = dbTable;
    }

    private static TTable fromXmlToObject(String filePath, Class theClass) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(theClass);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (TTable) un.unmarshal(new File(filePath));
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
            TTable<TR> tmpTable = fromXmlToObject(filePath, table.getClass());
            table.setListOfRows(tmpTable.getListOfRows());
            table.setReady(true);
            if (dbTable != null) {
                (new LoaderToBase<TR, DBTable<TTable<TR>, TR>>(table, dbTable)).run();
            }
        } else {
            //Истошно ругаемся
        }

    }
}
