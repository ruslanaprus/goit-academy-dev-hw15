package com.example.notemanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void shouldGenerateSequentialIds() {
        IdGenerator idGenerator = new IdGenerator();
        long firstId = idGenerator.generateId();
        long secondId = idGenerator.generateId();

        assertEquals(1, firstId, "the first id should be 1");
        assertEquals(2, secondId, "the second id should be 2");
    }

}