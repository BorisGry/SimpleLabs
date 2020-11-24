package ru.mtuci.simpleapi.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractBaseEntity {
    @NotBlank
    private String name;
    @NotBlank
    private String Brand;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;
}
