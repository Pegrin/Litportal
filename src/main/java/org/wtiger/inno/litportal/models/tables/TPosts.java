package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRPosts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class TPosts implements TTable<TRPosts> {
    @XmlElement(name = "Post")
    private ArrayList<TRPosts> listOfRows = null;

    public ArrayList<TRPosts> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayList<TRPosts> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
