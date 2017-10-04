package com.example.marlon.app_dhl.printerzebra.event;

/**
 * Created by Eukaris on 17/08/17.
 */

public class PrintEvent {
    public static final int enableTestButton=0;
    public static final int setStatus=1;

    private String statusMessage;
    private int color;
    private boolean enable;
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
