package org.smartinrubio.springboottransparentcache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOption {

    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "PAYMENT_OPTION_COUNTRY",
            joinColumns = @JoinColumn(name = "PAYMENT_OPTION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID"))
    private List<Country> countries;

    private String name;
}
