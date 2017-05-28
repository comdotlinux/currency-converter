package com.linux.cc.business.currencystore.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linux.cc.business.currencystore.entity.HistoricalConversion;

@Repository
public interface CurrencyStoreRepository extends JpaRepository<HistoricalConversion, Long>{
	@Query("select c from HistoricalConversion c where c.userId = ?1 order by c.id DESC")
	List<HistoricalConversion> getConversions(int userId);
}
