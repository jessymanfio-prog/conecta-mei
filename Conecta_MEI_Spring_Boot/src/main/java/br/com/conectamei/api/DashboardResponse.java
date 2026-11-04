package br.com.conectamei.api;
import java.math.BigDecimal;
public record DashboardResponse(String month,BigDecimal revenues,BigDecimal expenses,BigDecimal balance,long count) {}
