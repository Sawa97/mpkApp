package data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SingleInformation implements Serializable {
    @Id
    private int inforamtionId;
    @OneToOne
    private BusStop busStop;
    private Date date;

    public SingleInformation(int inforamtionId, BusStop busStop, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
