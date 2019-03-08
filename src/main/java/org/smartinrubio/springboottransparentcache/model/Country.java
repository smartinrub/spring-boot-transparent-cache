package org.smartinrubio.springboottransparentcache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "countries")
    private List<PaymentOption> paymentOptions;

    private String name;
}
