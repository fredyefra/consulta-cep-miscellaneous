package br.com.main.bean;

import java.io.Serializable;
import java.util.Objects;

public record EnderecoWrapper(String cep,
                              String logradouro,
                              String complemento,
                              String bairro,
                              String localidade,
                              String uf,
                              String ddd) implements Serializable {


}