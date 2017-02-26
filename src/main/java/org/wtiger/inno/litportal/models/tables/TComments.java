package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRComments;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;


@XmlRootElement(name = "Comments")
@XmlAccessorType(XmlAccessType.FIELD)
public class TComments implements TTable<TRComments> {
    @XmlElement(name = "Comment")
    private ArrayDeque<TRComments> listOfRows = null;
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

    public ArrayDeque<TRComments> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TRComments> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
