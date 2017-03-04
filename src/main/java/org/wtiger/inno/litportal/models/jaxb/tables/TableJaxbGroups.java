package org.wtiger.inno.litportal.models.jaxb.tables;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbGroups;
import org.wtiger.inno.litportal.models.tables.Table;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableJaxbGroups implements Table<TableRowJaxbGroups> {
    @XmlElement(name = "Group")
    private ArrayDeque<TableRowJaxbGroups> listOfRows = null;
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

    public ArrayDeque<TableRowJaxbGroups> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TableRowJaxbGroups> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
