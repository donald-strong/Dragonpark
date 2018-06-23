package au.com.dragon.db;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.dragon.rates.StandardRatePeriod;

@Transactional
public interface StandardRatePeriodRepository extends JpaRepository<StandardRatePeriod, String>{
}
