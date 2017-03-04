package org.wtiger.inno.litportal.models.jaxb.tables;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbComments;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;


@XmlRootElement(name = "Comments")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableJaxbComments implements Table<TableRowJaxbComments> {
    @XmlElement(name = "Comment")
    private ArrayDeque<TableRowJaxbComments> listOfRows = null;
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

    public ArrayDeque<TableRowJaxbComments> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TableRowJaxbComments> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
