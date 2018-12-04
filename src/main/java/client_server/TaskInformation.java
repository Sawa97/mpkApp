package client_server;

import java.io.Serializable;

public class TaskInformation implements Serializable {
    private int serverPort;
    private long seconds;
    private boolean empty = true;

    public TaskInformation(int serverPort, long seconds) {
        this.serverPort = serverPort;
        this.seconds = seconds;
        this.empty = false;

    }

    public TaskInformation() {
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.empty = false;
        this.serverPort = serverPort;
    }


    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.empty = false;
        this.seconds = seconds;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
