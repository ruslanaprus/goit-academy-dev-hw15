package com.example.notemanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorServiceTest {

    @Test
    void shouldGenerateSequentialIds() {
        IdGeneratorService idGeneratorService = new IdGeneratorService();
        long firstId = idGeneratorService.generateId();
        long secondId = idGeneratorService.generateId();

        assertEquals(1, firstId, "the first id should be 1");
        assertEquals(2, secondId, "the second id should be 2");
    }

}