package au.com.dragon.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.dragon.rates.FlatRatePeriod;

@Transactional
public interface FlatRatePeriodRepository extends JpaRepository<FlatRatePeriod, String>{
    
    List<FlatRatePeriod> findByRateName(String rateName);
}
