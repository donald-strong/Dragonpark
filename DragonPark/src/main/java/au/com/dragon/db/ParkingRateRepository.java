package au.com.dragon.db;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.dragon.rates.ParkingRate;

@Transactional
public interface ParkingRateRepository extends JpaRepository<ParkingRate, Long>{
}
