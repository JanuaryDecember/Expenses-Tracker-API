package pl.januarydecember.expensestrackerapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.januarydecember.expensestrackerapi.models.Expense;
import pl.januarydecember.expensestrackerapi.services.ExpensesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expenses")
public class ExpensesController {
    private final ExpensesService expensesService;

    @GetMapping("/getUserExpenses")
    public List<Expense> getUserExpenses(HttpServletRequest request) throws Exception {
        return expensesService.getUserExpenses(request);
    }

    @PostMapping("/addExpense")
    public void addExpense(@RequestBody Expense expense, HttpServletRequest request) throws Exception {
        expensesService.addExpense(expense, request);
    }
}