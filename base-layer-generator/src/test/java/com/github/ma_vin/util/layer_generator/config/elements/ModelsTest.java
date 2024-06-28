package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.elements.Models.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ModelsTest {

    @Test
    public void testInclude(){
        assertTrue(DOMAIN_DAO_DTO.includes(DAO), "DAO should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DTO), "DTO should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DOMAIN), "DOMAIN should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DAO_DTO), "DAO_DTO should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DOMAIN_DAO), "DOMAIN_DAO should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DOMAIN_DTO), "DOMAIN_DTO should be included by DOMAIN_DAO_DTO");
        assertTrue(DOMAIN_DAO_DTO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO should be included by DOMAIN_DAO_DTO");

        assertTrue(DAO.includes(DAO), "DAO should be included by DAO");
        assertFalse(DAO.includes(DTO), "DTO should not be included by DAO");
        assertFalse(DAO.includes(DOMAIN), "DOMAIN should not be included by DAO");
        assertFalse(DAO.includes(DAO_DTO), "DAO_DTO should not be included by DAO");
        assertFalse(DAO.includes(DOMAIN_DAO), "DOMAIN_DAO should not be included by DAO");
        assertFalse(DAO.includes(DOMAIN_DTO), "DOMAIN_DTO should not be included by DAO");
        assertFalse(DAO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DAO");

        assertFalse(DTO.includes(DAO), "DAO should not be included by DTO");
        assertTrue(DTO.includes(DTO), "DTO should be included by DTO");
        assertFalse(DTO.includes(DOMAIN), "DOMAIN should not be included by DTO");
        assertFalse(DTO.includes(DAO_DTO), "DAO_DTO should not be included by DTO");
        assertFalse(DTO.includes(DOMAIN_DAO), "DOMAIN_DAO should not be included by DTO");
        assertFalse(DTO.includes(DOMAIN_DTO), "DOMAIN_DTO should not be included by DTO");
        assertFalse(DTO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DTO");

        assertFalse(DOMAIN.includes(DAO), "DAO should not be included by DOMAIN");
        assertFalse(DOMAIN.includes(DTO), "DTO should not be included by DOMAIN");
        assertTrue(DOMAIN.includes(DOMAIN), "DOMAIN should be included by DOMAIN");
        assertFalse(DOMAIN.includes(DAO_DTO), "DAO_DTO should not be included by DOMAIN");
        assertFalse(DOMAIN.includes(DOMAIN_DAO), "DOMAIN_DAO should not be included by DOMAIN");
        assertFalse(DOMAIN.includes(DOMAIN_DTO), "DOMAIN_DTO should not be included by DOMAIN");
        assertFalse(DOMAIN.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DOMAIN");

        assertTrue(DAO_DTO.includes(DAO), "DAO should be included by DAO_DTO");
        assertTrue(DAO_DTO.includes(DTO), "DTO should  be included by DAO_DTO");
        assertFalse(DAO_DTO.includes(DOMAIN), "DOMAIN should not be included by DAO_DTO");
        assertTrue(DAO_DTO.includes(DAO_DTO), "DAO_DTO should be included by DAO_DTO");
        assertFalse(DAO_DTO.includes(DOMAIN_DAO), "DOMAIN_DAO should not be included by DAO_DTO");
        assertFalse(DAO_DTO.includes(DOMAIN_DTO), "DOMAIN_DTO should not be included by DAO_DTO");
        assertFalse(DAO_DTO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DAO_DTO");

        assertTrue(DOMAIN_DAO.includes(DAO), "DAO should be included by DOMAIN_DAO");
        assertFalse(DOMAIN_DAO.includes(DTO), "DTO should not be included by DOMAIN_DAO");
        assertTrue(DOMAIN_DAO.includes(DOMAIN), "DOMAIN should be included by DOMAIN_DAO");
        assertFalse(DOMAIN_DAO.includes(DAO_DTO), "DAO_DTO should not be included by DOMAIN_DAO");
        assertTrue(DOMAIN_DAO.includes(DOMAIN_DAO), "DOMAIN_DAO shouldbe included by DOMAIN_DAO");
        assertFalse(DOMAIN_DAO.includes(DOMAIN_DTO), "DOMAIN_DTO should not be included by DOMAIN_DAO");
        assertFalse(DOMAIN_DAO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DOMAIN_DAO");

        assertFalse(DOMAIN_DTO.includes(DAO), "DAO should not be included by DOMAIN_DTO");
        assertTrue(DOMAIN_DTO.includes(DTO), "DTO should be included by DOMAIN_DTO");
        assertTrue(DOMAIN_DTO.includes(DOMAIN), "DOMAIN should be included by DOMAIN_DTO");
        assertFalse(DOMAIN_DTO.includes(DAO_DTO), "DAO_DTO should not be included by DOMAIN_DTO");
        assertFalse(DOMAIN_DTO.includes(DOMAIN_DAO), "DOMAIN_DAO should not be included by DOMAIN_DTO");
        assertTrue(DOMAIN_DTO.includes(DOMAIN_DTO), "DOMAIN_DTO should be included by DOMAIN_DTO");
        assertFalse(DOMAIN_DTO.includes(DOMAIN_DAO_DTO), "DOMAIN_DAO_DTO not should be included by DOMAIN_DTO");
    }
}
