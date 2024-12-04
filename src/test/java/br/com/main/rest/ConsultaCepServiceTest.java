package br.com.main.rest;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;



@QuarkusTest
public class ConsultaCepServiceTest {

    @Test
    public void testDevolverEndpointEnderecoPorCep() {

        //RestAssured.baseURI = "http://localhost:8080/cep";

        final String cep = "70390-055";

        ValidatableResponse body = RestAssured.given()
                .pathParam("cep", cep)
                .when().get("/cep/{cep}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("bairro", equalTo("Asa Sul"))
                .body("ddd", equalTo("61"))
                .body("localidade", equalTo("Brasília"));

    }
    @Test
    public void testDevolverEndpointEnderecoPorUfCidadeLogradouro() {

        final String uf = "DF";
        final String cidade = "Brasília";
        final String logradouro = "SAAN";

        RestAssured.given()
                .pathParam("uf", uf)
                .pathParam("cidade", cidade)
                .pathParam("logradouro", logradouro)
                .when().get("/cep/{uf}/{cidade}/{logradouro}")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(16));
    }

    @Test
    public void testDevolverEndpointNaoLocalizado() {

        final String cep = "70390-055";

        RestAssured.given()
                .pathParam("cep", cep)
                .when().get("/cepp/{cep}")
                .then()
                .statusCode(404);

    }
}