package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRComments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement(name = "Comments")
@XmlAccessorType(XmlAccessType.FIELD)
public class TComments implements TTable<TRComments> {
    @XmlElement(name = "Comment")
    private ArrayList<TRComments> listOfRows = null;

    public ArrayList<TRComments> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayList<TRComments> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
