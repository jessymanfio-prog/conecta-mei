package br.com.conectamei.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="transactions")
public class TransactionEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private TransactionType type;
 @Column(nullable=false,length=60) private String category;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 @Column(name="transaction_date",nullable=false) private LocalDate date;
 public TransactionEntity(){}
 public TransactionEntity(String description,TransactionType type,String category,BigDecimal amount,LocalDate date){this.description=description;this.type=type;this.category=category;this.amount=amount;this.date=date;}
 public Long getId(){return id;}
 public String getDescription(){return description;}
 public TransactionType getType(){return type;}
 public String getCategory(){return category;}
 public BigDecimal getAmount(){return amount;}
 public LocalDate getDate(){return date;}
}
