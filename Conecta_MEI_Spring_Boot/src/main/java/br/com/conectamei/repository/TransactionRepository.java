package br.com.conectamei.repository;
import br.com.conectamei.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long>{
 List<TransactionEntity> findAllByOrderByDateDescIdDesc();
 List<TransactionEntity> findByDateGreaterThanEqualAndDateLessThanOrderByDateDescIdDesc(LocalDate start,LocalDate end);
}
