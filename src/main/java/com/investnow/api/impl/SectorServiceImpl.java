package com.investnow.api.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investnow.api.service.SectorService;
import com.investnow.dao.model.Sector;
import com.investnow.dao.repository.SectorRepository;

@Service
public class SectorServiceImpl implements SectorService
{
    private SectorRepository sectorRepository;

    @Autowired
    public SectorServiceImpl(SectorRepository sectorRepository)
    {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> getAllSectors()
    {
        return sectorRepository.findAll();
    }

    @Override
    public Sector addSector(Sector sector) throws Exception
    {
        List<Sector> existingSectors = sectorRepository.findBySectorName(sector.getSectorName());
        if(CollectionUtils.isNotEmpty(existingSectors))
        {
            throw new Exception("Sector " + sector.getSectorName() + " already exists");
        }
        return sectorRepository.save(sector);
    }
}
