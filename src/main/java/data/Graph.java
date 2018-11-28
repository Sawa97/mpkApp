package data;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Graph implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int Id;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BusStop> busStops;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Connection> connections;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Line> lineList;


    public Graph(List<BusStop> bustops, List<Connection> connections, List<Line> lineList) {
        this.busStops = bustops;
        this.connections = connections;
        this.lineList = lineList;
    }

    public Graph() {
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
