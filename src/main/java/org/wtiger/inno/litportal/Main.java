package org.wtiger.inno.litportal;

import org.wtiger.inno.litportal.dbtools.DBPosts;
import org.wtiger.inno.litportal.dbtools.DBUsers;
import org.wtiger.inno.litportal.models.tables.TPosts;
import org.wtiger.inno.litportal.models.tables.TUsers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static Connection getConPostgres(String host, String port, String db, String user) throws SQLException {
        String password = "hrenasword";
        return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db + "",
                user, password);
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection con = getConPostgres("localhost", "5432",
                "litportal", "postgres")) {
            DBUsers dbUsers = new DBUsers(con);
            TUsers tUsers = dbUsers.getObjects(dbUsers.getFullInsertStatement());
            DBPosts dbPosts = new DBPosts(con);
            TPosts tPosts = dbPosts.getObjects(dbPosts.getFullInsertStatement());
            convertObjectToXml(tUsers, "tUsers.xml");
            convertObjectToXml(tPosts, "tPosts.xml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TUsers newUsers = (TUsers) fromXmlToObject("tUsers.xml", TUsers.class);
        TPosts newPosts = (TPosts) fromXmlToObject("tPosts.xml", TPosts.class);
        System.out.println();
    }

    private static Object fromXmlToObject(String filePath, Class theClass) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(theClass);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Integer i = 0;
        System.out.println(i.intValue());
        return null;
    }

    // сохраняем объект в XML файл
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
}
