package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRGroups;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class TGroups implements TTable<TRGroups> {
    @XmlElement(name = "Group")
    private ArrayList<TRGroups> listOfRows = null;

    public ArrayList<TRGroups> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayList<TRGroups> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
