package br.com.conectamei.service;
import br.com.conectamei.api.*;
import br.com.conectamei.domain.*;
import br.com.conectamei.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
@Service
public class FinanceService {
 private final TransactionRepository transactions;
 private final ProductRepository products;
 public FinanceService(TransactionRepository transactions,ProductRepository products){this.transactions=transactions;this.products=products;}
 @Transactional(readOnly=true) public List<TransactionEntity> listTransactions(){return transactions.findAllByOrderByDateDescIdDesc();}
 @Transactional public TransactionEntity create(TransactionRequest r){return transactions.save(new TransactionEntity(r.description().trim(),r.type(),r.category().trim(),r.amount(),r.date()));}
 @Transactional public void deleteTransaction(long id){if(!transactions.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Lançamento não encontrado");transactions.deleteById(id);}
 @Transactional(readOnly=true) public List<ProductEntity> listProducts(){return products.findAllByOrderByIdDesc();}
 @Transactional public ProductEntity createProduct(ProductRequest r){return products.save(new ProductEntity(r.name().trim(),r.type(),r.price()));}
 @Transactional public void deleteProduct(long id){if(!products.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto ou serviço não encontrado");products.deleteById(id);}
 @Transactional(readOnly=true) public DashboardResponse dashboard(YearMonth month){
  var records=transactions.findByDateGreaterThanEqualAndDateLessThanOrderByDateDescIdDesc(month.atDay(1),month.plusMonths(1).atDay(1));
  BigDecimal revenue=BigDecimal.ZERO,expense=BigDecimal.ZERO;
  for(var t:records){if(t.getType()==TransactionType.RECEITA)revenue=revenue.add(t.getAmount());else expense=expense.add(t.getAmount());}
  return new DashboardResponse(month.toString(),revenue,expense,revenue.subtract(expense),records.size());
 }
}
