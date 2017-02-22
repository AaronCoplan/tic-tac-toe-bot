package com.tictactoebot.gameEngine;

/**
 * Created by Devin on 2/21/2017.
 */
public class ConnectionInfo {
    private boolean isClient;
    private boolean isHost;

    private String ip;
    private int port;

    public ConnectionInfo(String ip, int port){    //Only needs an ip if it is the client.
        isClient = true;

        this.ip = ip;
        this.port = port;
    }

    public ConnectionInfo(int port){    //If it only needs a port -> must be the host
        isHost = true;
        this.port = port;
    }

    public void isClient(boolean isClient){
        this.isClient = isClient;
        this.isHost = !isClient;
    }

    public void isHost(boolean isHost){
        this.isClient = !isHost;
        this.isHost = isHost;
    }

    public boolean isClient(){
        return isClient;
    }

    public boolean isHost(){
        return isHost;
    }

    public String getIp(){
        return ip;
    }

    public int getPort(){
        return port;
    }
}
