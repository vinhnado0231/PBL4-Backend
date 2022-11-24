package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.payload.JwtResponse;
import com.example.backend.payload.LoginRequest;
import com.example.backend.service.impl.AccountService;
import com.example.backend.ultil.ForgotPasswordToken;
import com.example.backend.ultil.JwtUtility;
import com.example.backend.ultil.LoginSession;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private AccountService accountService;

    public static Set<LoginSession> loginSessionSet = new LinkedHashSet<>();

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = accountService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtility.generateJwtToken(userDetails.getUsername());
        Account account = accountService.getAccountByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, account.getIdAccount(), account.getUsername()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            creatLoginSession(username);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private void creatLoginSession(String username){
        try{
            String token = RandomString.make(45);
            loginSessionSet.add(new LoginSession(accountService.getIdUserByUsername(username),token, LocalDateTime.now().plusMinutes(30)));
        }catch (Exception e){
            creatLoginSession(username);
        }
    }
}
