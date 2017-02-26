package org.wtiger.inno.litportal.workers;

import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.TTable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by olymp on 21.02.2017.
 */
public class LoaderToXML<TR extends TableRow> implements Runnable {
    private final TTable<TR> table;
    private String filePath;

    public LoaderToXML(TTable<TR> table, String filePath) {
        this.table = table;
        this.filePath = filePath;
    }

    private static void convertObjectToXml(Object obj, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // маршаллинг объекта в файл
            marshaller.marshal(obj, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        convertObjectToXml(table, filePath);
    }
}
