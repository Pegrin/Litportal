package org.wtiger.inno.litportal.models.jaxb.tables;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbUsers;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableJaxbUsers implements Table<TableRowJaxbUsers> {
    @XmlElement(name = "User")
    private ArrayDeque<TableRowJaxbUsers> listOfRows = null;
    @XmlTransient
    private boolean ready = true;

    @Override
    public synchronized boolean isReady() {
        return ready;
    }

    @Override
    public synchronized void setReady(boolean ready) {
        this.ready = ready;
    }

    public ArrayDeque<TableRowJaxbUsers> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TableRowJaxbUsers> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
