package br.com.main.rest;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@Disabled
@QuarkusTest
public class ConsultaCepResourceTest {

  @Test
  public void testeDeveConsultarPorCep(){

    String cep = "71720585";

    //EnderecoWrapper enderecoWrapper = new EnderecoWrapper(cep);

    io.restassured
            .RestAssured
            .given().contentType(ContentType.JSON)
            .pathParam("cep" ,cep)
            .when()
            .get("/cep/{cep}")
            .then().statusCode(200);
            //.body(is(enderecoWrapper));

    /*{
      "bairro": "Núcleo Bandeirante",
            "cep": "71720-585",
            "complemento": "",
            "ddd": "61",
            "localidade": "Brasília",
            "logradouro": "Terceira Avenida Área Especial 2",
            "uf": "DF"
    }*/



  }

}
