package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRGroups;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class TGroups implements TTable<TRGroups> {
    @XmlElement(name = "Group")
    private ArrayDeque<TRGroups> listOfRows = null;
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

    public ArrayDeque<TRGroups> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TRGroups> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
