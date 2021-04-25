package com.meli.test.dnamutantanalyzer.gateway.persistence.repository;

import com.meli.test.dnamutantanalyzer.gateway.persistence.entity.StatsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * StatRepository manage de interaction with the persistence for Stats
 */
@Repository
public interface StatRepository extends CrudRepository<StatsEntity, Integer> {

}
