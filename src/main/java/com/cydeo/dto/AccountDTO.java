package com.cydeo.dto;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;

    @NotNull
    @Positive
    private BigDecimal balance;

    @NotNull
    private AccountType accountType;

    private Date creationDate;

    @NotNull
    private Long userId;

    private AccountStatus accountStatus;
}
