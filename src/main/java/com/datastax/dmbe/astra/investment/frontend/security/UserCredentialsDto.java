package com.datastax.dmbe.astra.investment.frontend.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDto {

  private String userName;
  private String password;
  
}
