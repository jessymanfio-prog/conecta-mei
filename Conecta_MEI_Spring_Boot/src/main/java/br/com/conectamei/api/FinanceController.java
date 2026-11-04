package br.com.conectamei.api;
import br.com.conectamei.domain.*;
import br.com.conectamei.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;
@RestController @RequestMapping("/api")
public class FinanceController {
 private final FinanceService service;
 public FinanceController(FinanceService service){this.service=service;}
 @GetMapping("/transactions") public List<TransactionEntity> transactions(){return service.listTransactions();}
 @PostMapping("/transactions") public ResponseEntity<TransactionEntity> create(@Valid @RequestBody TransactionRequest body){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(body));}
 @DeleteMapping("/transactions/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable long id){service.deleteTransaction(id);}
 @GetMapping("/products") public List<ProductEntity> products(){return service.listProducts();}
 @PostMapping("/products") public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductRequest body){return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(body));}
 @DeleteMapping("/products/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteProduct(@PathVariable long id){service.deleteProduct(id);}
 @GetMapping("/dashboard") public DashboardResponse dashboard(@RequestParam String month){try{return service.dashboard(YearMonth.parse(month));}catch(java.time.format.DateTimeParseException e){throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST,"Mês inválido. Use AAAA-MM");}}
}
