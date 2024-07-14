package com.cydeo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @NotNull
    private AccountDTO sender;

    @NotNull
    private AccountDTO receiver;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    @Size(min = 2,max = 250)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String message;

    private Date createDate;


}
