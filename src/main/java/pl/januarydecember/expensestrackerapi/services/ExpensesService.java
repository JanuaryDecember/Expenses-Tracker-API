package pl.januarydecember.expensestrackerapi.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.januarydecember.expensestrackerapi.jwt.JwtService;
import pl.januarydecember.expensestrackerapi.models.Expense;
import pl.januarydecember.expensestrackerapi.models.User;
import pl.januarydecember.expensestrackerapi.repositories.ExpenseRepository;
import pl.januarydecember.expensestrackerapi.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpensesService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private User getUser(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        Optional<User> user = userRepository.findByUsername(jwtService.extractUsername(jwt));
        return user.orElse(null);
    }

    public List<Expense> getUserExpenses(HttpServletRequest request) throws Exception {
        User user = getUser(request);
        if (user != null)
            return expenseRepository.findByUserId(user.getId());
        throw new Exception("Nie udało się pobrać listy wydatków");
    }

    public void addExpense(Expense expense, HttpServletRequest request) throws Exception {
        User user = getUser(request);
        if (user != null) {
            expense.setUserId(user.getId());
            expenseRepository.save(expense);
        } else
            throw new Exception("Nie udało się dodać wydatku do konta!");
    }
}
