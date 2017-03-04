package org.wtiger.inno.litportal.workers;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class LoaderToXML<TR extends TableRow> implements Runnable {
    private static Logger logger = Logger.getLogger(LoaderToXML.class);
    private final Table<TR> table;
    private String filePath;

    public LoaderToXML(Table<TR> table, String filePath) {
        this.table = table;
        this.filePath = filePath;
    }

    private static void convertObjectToXml(Object obj, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (table.isReady()) {
            table.setReady(false);
            convertObjectToXml(table, filePath);
            table.setReady(true);
        } else
            logger.error("Не удалось обработать таблицу. Таблица была заблокирована.");
    }
}
