package org.wtiger.inno.litportal.models.tables;

import org.wtiger.inno.litportal.models.rows.TRRating;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Rating")
@XmlAccessorType(XmlAccessType.FIELD)
@Deprecated
public class TRating implements TTable<TRRating> {
    @XmlElement(name = "RatingRow")
    private ArrayList<TRRating> listOfRows = null;

    public ArrayList<TRRating> getListOfRows() {
        return listOfRows;
    }

    public void setListOfRows(ArrayList<TRRating> listOfRows) {
        this.listOfRows = listOfRows;
    }
}
