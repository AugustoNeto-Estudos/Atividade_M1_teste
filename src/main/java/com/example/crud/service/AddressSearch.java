package com.example.crud.service;

import com.example.crud.domain.address.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressSearch {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AddressSearch(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String searchAddress(String state, String city, String street) {
        String url = "https://viacep.com.br/ws/{state}/{city}/{street}/json/";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("state", state);
        uriVariables.put("city", city);
        uriVariables.put("street", street);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);

        try {
            List<Address> addresses = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Address.class));
            String cep = addresses.get(0).getCep();
            return cep;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Serviço criado, consumindo a API ViaCEP - #3
    public String searchByCep(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep.replaceAll("\\D", ""));
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Address address = objectMapper.readValue(response.getBody(), Address.class);
            return address.getLocalidade();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Logica dentro da service, que retorna uma resposta Boolean, baseado na CIDADE do CEP enviado x Distribution_Center do Produto,
    //Caso sejam iguais: Retorna True - #6
    public boolean isCepMatchingDistributionCenter(String cep, String distribution_center) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep.replaceAll("\\D", ""));
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Address address = objectMapper.readValue(response.getBody(), Address.class);

            String cidadeDoCep = address.getLocalidade(); // cidade retornada pelo ViaCEP

            // Comparação ignorando maiúsculas/minúsculas e espaços extras
            return cidadeDoCep != null && cidadeDoCep.trim().equalsIgnoreCase(distribution_center.trim());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

