package com.example.crud.service;

import com.example.crud.domain.address.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.Normalizer;

//Serviço criado, consumindo a API ViaCEP - #3
@Service
public class ViaCepService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ViaCepService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String buscarCidadePorCep(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep.replaceAll("\\D", ""));
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Address address = objectMapper.readValue(response.getBody(), Address.class);
            return address.getLocalidade(); // Retorna só a cidade
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Logica dentro da service, que retorna uma resposta Boolean, baseado na CIDADE do CEP enviado x Distribution_Center do Produto,
    //Caso sejam iguais: Retorna True - #6
    public boolean isCepMatchingDistributionCenter(String cep, String distributionCenter) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep.replaceAll("\\D", ""));
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Address address = objectMapper.readValue(response.getBody(), Address.class);

            String cidadeDoCep = address.getLocalidade();

            return cidadeDoCep != null &&
                    normaliza(cidadeDoCep).equals(normaliza(distributionCenter));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String normaliza(String valor) {
        if (valor == null) return "";
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}

