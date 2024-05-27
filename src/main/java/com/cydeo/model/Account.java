package com.cydeo.model;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;

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
