// migration criada e aplicada, criando campo na tabela product chamado "distribution_center",
//E sete manualmente o valor deste campo para os produtos existentes - #1 na V_5
package com.example.crud.domain.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name="product")
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer price;

    private Boolean active;

    private String category;

    private String distribution_center;

    public Product(RequestProduct requestProduct){
        this.name = requestProduct.name();
        this.price = requestProduct.price();
        this.category = requestProduct.category();
        this.active = true;
        this.distribution_center = requestProduct.distribution_center(); // campo novo - #2
    }

    // outros campos...@Column(name = "distribution_center")private String distribution_center;

    // getters e setters do campo novo - #2
    public String getDistributionCenter() {
        return distribution_center;
    }
    public void setDistributionCenter(String distributionCenter) {
        this.distribution_center = distributionCenter;
    }
}

