package br.com.conectamei.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="products")
public class ProductEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80) private String name;
 @Column(nullable=false,length=12) private String type;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal price;
 public ProductEntity(){}
 public ProductEntity(String name,String type,BigDecimal price){this.name=name;this.type=type;this.price=price;}
 public Long getId(){return id;}
 public String getName(){return name;}
 public String getType(){return type;}
 public BigDecimal getPrice(){return price;}
}
