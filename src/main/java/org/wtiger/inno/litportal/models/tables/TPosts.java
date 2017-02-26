package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRPosts;

import javax.xml.bind.annotation.*;
import java.util.ArrayDeque;

@XmlRootElement(name = "Posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class TPosts implements TTable<TRPosts> {
    @XmlElement(name = "Post")
    private ArrayDeque<TRPosts> listOfRows = null;
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

    public ArrayDeque<TRPosts> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayDeque<TRPosts> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
