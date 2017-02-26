package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRUsers;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class TUsers implements TTable<TRUsers> {
    @XmlElement(name = "User")
    private ArrayDeque<TRUsers> listOfRows = null;
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

    public ArrayDeque<TRUsers> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TRUsers> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
