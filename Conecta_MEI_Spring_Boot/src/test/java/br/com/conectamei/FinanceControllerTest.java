package br.com.conectamei;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class FinanceControllerTest {
 @Autowired MockMvc mvc;
 @Test void consultarDashboard() throws Exception {mvc.perform(get("/api/dashboard").param("month","2026-11")).andExpect(status().isOk()).andExpect(jsonPath("$.month").value("2026-11"));}
 @Test void rejeitarValorNegativo() throws Exception {String body=""" 
 {"description":"Teste","type":"RECEITA","category":"Vendas","amount":-5,"date":"2026-11-01"}
 """;mvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());}
 @Test void criarELerLancamento() throws Exception {String body="""
 {"description":"Venda teste","type":"RECEITA","category":"Vendas","amount":99.90,"date":"2026-11-01"}
 """;mvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber());mvc.perform(get("/api/transactions")).andExpect(status().isOk());}
}
