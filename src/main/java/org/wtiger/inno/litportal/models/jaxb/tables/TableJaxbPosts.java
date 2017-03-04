package org.wtiger.inno.litportal.models.jaxb.tables;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbPosts;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableJaxbPosts implements Table<TableRowJaxbPosts> {
    @XmlElement(name = "Post")
    private ArrayDeque<TableRowJaxbPosts> listOfRows = null;
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

    public ArrayDeque<TableRowJaxbPosts> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TableRowJaxbPosts> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
