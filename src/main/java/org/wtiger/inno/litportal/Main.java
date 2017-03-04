package org.wtiger.inno.litportal;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbComments;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbGroups;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbPosts;
import org.wtiger.inno.litportal.dbtools.Jaxb.DBJaxbUsers;
import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbComments;
import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbGroups;
import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbPosts;
import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbUsers;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbComments;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbGroups;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbPosts;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbUsers;
import org.wtiger.inno.litportal.workers.UploaderFromBase;
import org.wtiger.inno.litportal.workers.UploaderFromXML;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class Main {

    Logger logger = Logger.getLogger(this.getClass());

    public static void main(String[] args) {
        String host = "localhost", port = "5432", db = "litportal";
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + db + "");
            cpds.setUser("postgres");
            cpds.setPassword("password");
            cpds.setMinPoolSize(4);
            cpds.setAcquireIncrement(2);
            cpds.setMaxPoolSize(8);
            Thread threadU;
            Thread threadG;
            Thread threadP;
            Thread threadC;

            //Выгружаем все в XML
            threadU = new Thread(new UploaderFromBase<TableRowJaxbUsers, DBJaxbUsers>(
                    new TableJaxbUsers(), new DBJaxbUsers(cpds.getConnection()), "tUsers.xml"));
            threadU.start();
            threadG = new Thread(new UploaderFromBase<TableRowJaxbGroups, DBJaxbGroups>(
                    new TableJaxbGroups(), new DBJaxbGroups(cpds.getConnection()), "tGroup.xml"));
            threadG.start();
            threadP = new Thread(new UploaderFromBase<TableRowJaxbPosts, DBJaxbPosts>(
                    new TableJaxbPosts(), new DBJaxbPosts(cpds.getConnection()), "tPosts.xml"));
            threadP.start();
            threadC = new Thread(new UploaderFromBase<TableRowJaxbComments, DBJaxbComments>(
                    new TableJaxbComments(), new DBJaxbComments(cpds.getConnection()), "tComments.xml"));
            threadC.start();
            threadU.join();
            threadG.join();
            threadP.join();
            threadC.join();

            //Чистим базу
            (new DBJaxbComments(cpds.getConnection())).deleteAll();
            (new DBJaxbPosts(cpds.getConnection())).deleteAll();
            (new DBJaxbGroups(cpds.getConnection())).deleteAll();
            (new DBJaxbUsers(cpds.getConnection())).deleteAll();

            //А тут загрузим все это обратно.
            threadU = new Thread(new UploaderFromXML<TableRowJaxbUsers>(new TableJaxbUsers(), "tUsers.xml", new DBJaxbUsers(cpds.getConnection())), "Users");
            threadU.start();
            threadG = new Thread(new UploaderFromXML<TableRowJaxbGroups>(new TableJaxbGroups(), "tGroup.xml", new DBJaxbGroups(cpds.getConnection())), "Groups");
            threadG.start();
            threadU.join();
            threadG.join();
            (new UploaderFromXML<TableRowJaxbPosts>(new TableJaxbPosts(), "tPosts.xml", new DBJaxbPosts(cpds.getConnection()))).run();
            (new UploaderFromXML<TableRowJaxbComments>(new TableJaxbComments(), "tComments.xml", new DBJaxbComments(cpds.getConnection()))).run();

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
