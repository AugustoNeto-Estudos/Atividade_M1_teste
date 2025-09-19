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

}