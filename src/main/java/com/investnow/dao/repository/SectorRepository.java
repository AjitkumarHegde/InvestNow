package com.investnow.dao.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.investnow.dao.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Serializable>, JpaSpecificationExecutor<Sector>
{
    List<Sector> findBySectorName(String sectorName);
}
