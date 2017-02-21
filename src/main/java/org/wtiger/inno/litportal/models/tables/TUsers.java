package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRUsers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class TUsers implements TTable<TRUsers> {
    @XmlElement(name = "User")
    private ArrayList<TRUsers> listOfRows = null;

    public ArrayList<TRUsers> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayList<TRUsers> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
