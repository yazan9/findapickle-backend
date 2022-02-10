package com.findapickle.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    private static final long serialVersionUID = 5926468583005150707L;
    private String email;
    private String password;
}
