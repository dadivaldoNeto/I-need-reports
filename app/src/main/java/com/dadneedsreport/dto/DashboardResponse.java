package com.dadneedsreport.dto;


/*
{
  "amountIncome": "AOA 250000",
  "nIncome" : 2,
  "amountExpenses": "AOA 250000",
  "nExpenses" : 32,
  "currentBalance": 175000,
}
*/
public record DashboardResponse (String amountIncome, Long nIncomes, String amountExpenses, Long nExpenses, String currentBalance){}
