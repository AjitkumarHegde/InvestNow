package com.investnow.api.service;

import java.util.List;

import com.investnow.dao.model.Sector;

public interface SectorService
{
    /**
     * Fetch the sectors from datastore
     * @return
     */
    public List<Sector> getAllSectors();

    /**
     * Add a sector to the datastore
     * @param {@link} Sector
     * @return
     */
    public Sector addSector(Sector sector) throws Exception;
}
