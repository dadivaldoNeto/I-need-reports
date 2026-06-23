package com.dadneedsreport.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users_transactions")
@NoArgsConstructor
@Getter
public class Transaction extends BaseTransact {}
