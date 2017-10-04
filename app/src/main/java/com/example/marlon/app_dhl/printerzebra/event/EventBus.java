package com.example.marlon.app_dhl.printerzebra.event;

/**
 * Created by root on 01/03/17.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
    void postSticky(Object event);

}
