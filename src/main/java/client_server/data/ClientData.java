package client_server.data;

import data.Line;

import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private List<Line> allLines;
    private Line searchedLine;

    public ClientData() {
        allLines = new ArrayList<>();
    }

    public List<Line> getAllLines() {
        return allLines;
    }

    public void setAllLines(List<Line> allLines) {
        this.allLines = allLines;
    }

    public List<Line> searchLines(String condition){
        List<Line> resultList = new ArrayList<>();
        for(Line line: allLines){
            if(line.getLineNumber().startsWith(condition)){
                resultList.add(line);
            }
        }
        return resultList;
    }

    public Line getSearchedLine() {
        return searchedLine;
    }

    public void setSearchedLine(String number) {
        for(Line line: allLines){
            if(line.getLineNumber().equals(number)){
                this.searchedLine = line;
            }
        }
    }
}
