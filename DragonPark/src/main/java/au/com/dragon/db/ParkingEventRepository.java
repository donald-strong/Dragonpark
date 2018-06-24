package au.com.dragon.db;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.dragon.parking.ParkingEvent;

@Transactional
public interface ParkingEventRepository extends JpaRepository<ParkingEvent, Long>{
}
