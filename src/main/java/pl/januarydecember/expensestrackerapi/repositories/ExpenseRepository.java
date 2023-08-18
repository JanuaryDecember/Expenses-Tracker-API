package pl.januarydecember.expensestrackerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.januarydecember.expensestrackerapi.models.Expense;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
}
