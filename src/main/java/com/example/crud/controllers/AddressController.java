package com.example.crud.controllers;


import com.example.crud.service.AddressSearch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressSearch addressSearch;

    public AddressController(AddressSearch addressSearch) {
        this.addressSearch = addressSearch;
    }

    // GET /api/address/search?state=SP&city=Mogi das Cruzes&street=Rua Ipiranga
    @GetMapping("/search")
    public ResponseEntity<String> searchAddress(
            @RequestParam String state,
            @RequestParam String city,
            @RequestParam String street) {

        String cep = addressSearch.searchAddress(state, city, street);
        return ResponseEntity.ok(cep);
    }

    @GetMapping("/match")
    public ResponseEntity<Boolean> matchCep(
            @RequestParam String cep,
            @RequestParam String distribution_center) {
        //O endpoint chama o metodo da service - #5
        boolean result = addressSearch.isCepMatchingDistributionCenter(cep, distribution_center);
        return ResponseEntity.ok(result);
        // Exemplo de url no postman: http://localhost:8080/api/address/match?cep=08773380&distribution_center=Mogi das Cruzes
        // Request criada e testada - #7
    }

}