package data;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class SingleInformation implements Serializable {
    @Id
    private int inforamtionId;
    @OneToOne
    private BusStop busStop;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    public SingleInformation(int inforamtionId, BusStop busStop, DateTime date) {
        this.inforamtionId = inforamtionId;
        this.busStop = busStop;
        this.date = date;
    }

    public SingleInformation() {
    }

    public int getInforamtionId() {
        return inforamtionId;
    }

    public void setInforamtionId(int inforamtionId) {
        this.inforamtionId = inforamtionId;
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public void setBusStop(BusStop busStop) {
        this.busStop = busStop;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
