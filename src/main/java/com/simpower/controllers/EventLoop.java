package com.simpower.controllers;

import com.simpower.models.time.Clock;

public class EventLoop extends Thread{
    private Clock clock;

    public EventLoop(Clock clock){
        this.clock = clock;
    }

    public void run(){
        while(true){

        }
    }
}
