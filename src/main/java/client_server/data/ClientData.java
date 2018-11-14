package client_server.data;

import data.Line;

import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private List<Line> allLines;

    public ClientData() {
        allLines = new ArrayList<>();
    }

    public List<Line> getAllLines() {
        return allLines;
    }

    public void setAllLines(List<Line> allLines) {
        this.allLines = allLines;
    }
}
