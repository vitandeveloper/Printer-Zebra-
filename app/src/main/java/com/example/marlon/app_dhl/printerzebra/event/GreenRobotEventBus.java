package com.example.marlon.app_dhl.printerzebra.event;



/**
 * Created by root on 01/03/17.
 */

public class GreenRobotEventBus implements EventBus {
    org.greenrobot.eventbus.EventBus eventBus;

    public GreenRobotEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }

    @Override
    public void postSticky(Object event) {
        eventBus.postSticky(event);
    }
}
