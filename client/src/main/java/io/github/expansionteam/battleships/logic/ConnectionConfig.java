package io.github.expansionteam.battleships.logic;

public class ConnectionConfig {

    private static final String LOCAL_SERVER_IP = "127.0.0.1";
    private String serverIp = LOCAL_SERVER_IP;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
