package org.wtiger.inno.litportal;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DBComments;
import org.wtiger.inno.litportal.dbtools.DBGroups;
import org.wtiger.inno.litportal.dbtools.DBPosts;
import org.wtiger.inno.litportal.dbtools.DBUsers;
import org.wtiger.inno.litportal.models.rows.TRComments;
import org.wtiger.inno.litportal.models.rows.TRGroups;
import org.wtiger.inno.litportal.models.rows.TRPosts;
import org.wtiger.inno.litportal.models.rows.TRUsers;
import org.wtiger.inno.litportal.models.tables.TComments;
import org.wtiger.inno.litportal.models.tables.TGroups;
import org.wtiger.inno.litportal.models.tables.TPosts;
import org.wtiger.inno.litportal.models.tables.TUsers;
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
            threadU = new Thread(new UploaderFromBase<TRUsers, DBUsers>(
                    new TUsers(), new DBUsers(cpds.getConnection()), "tUsers.xml"));
            threadU.start();
            threadG = new Thread(new UploaderFromBase<TRGroups, DBGroups>(
                    new TGroups(), new DBGroups(cpds.getConnection()), "tGroup.xml"));
            threadG.start();
            threadP = new Thread(new UploaderFromBase<TRPosts, DBPosts>(
                    new TPosts(), new DBPosts(cpds.getConnection()), "tPosts.xml"));
            threadP.start();
            threadC = new Thread(new UploaderFromBase<TRComments, DBComments>(
                    new TComments(), new DBComments(cpds.getConnection()), "tComments.xml"));
            threadC.start();
            threadU.join();
            threadG.join();
            threadP.join();
            threadC.join();

            //Чистим базу
            (new DBComments(cpds.getConnection())).deleteAll();
            (new DBPosts(cpds.getConnection())).deleteAll();
            (new DBGroups(cpds.getConnection())).deleteAll();
            (new DBUsers(cpds.getConnection())).deleteAll();

            //А тут загрузим все это обратно.
            threadU = new Thread(new UploaderFromXML<TRUsers>(new TUsers(), "tUsers.xml", new DBUsers(cpds.getConnection())), "Users");
            threadU.start();
            threadG = new Thread(new UploaderFromXML<TRGroups>(new TGroups(), "tGroup.xml", new DBGroups(cpds.getConnection())), "Groups");
            threadG.start();
            threadU.join();
            threadG.join();
            (new UploaderFromXML<TRPosts>(new TPosts(), "tPosts.xml", new DBPosts(cpds.getConnection()))).run();
            (new UploaderFromXML<TRComments>(new TComments(), "tComments.xml", new DBComments(cpds.getConnection()))).run();

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
