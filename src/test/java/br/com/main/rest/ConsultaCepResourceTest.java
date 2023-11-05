package br.com.main.rest;


import br.com.main.bean.EnderecoWrapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ConsultaCepResourceTest {

  @Test
  public void testeDeveConsultarPorCep(){

    String cep = "71720585";

    //EnderecoWrapper enderecoWrapper = new EnderecoWrapper(cep);

    io.restassured
            .RestAssured
            .given().pathParam("cep" ,cep)
            .when()
            .get("/cep/{cep}")
            .then().statusCode(200);
            //.body(is(enderecoWrapper));
  }

}
