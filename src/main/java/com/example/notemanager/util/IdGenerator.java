package com.example.notemanager.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {
    private  final AtomicLong idGenerator = new AtomicLong(0);

    public long generateId(){
        return idGenerator.incrementAndGet();
    }
}
