package com.dadneedsreport.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users_transactions")
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
public class Transaction extends BaseTransact {}
