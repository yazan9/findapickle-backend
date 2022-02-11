package com.findapickle.backend.controllers;

import com.findapickle.backend.entities.UserEntity;
import com.findapickle.backend.exceptions.BadRequestException;
import com.findapickle.backend.exceptions.DuplicateEntryException;
import com.findapickle.backend.exceptions.EntityNotFoundException;
import com.findapickle.backend.exceptions.UnknownErrorException;
import com.findapickle.backend.models.User;
import com.findapickle.backend.repositories.UsersRepository;
import com.findapickle.backend.requests.JwtRequest;
import com.findapickle.backend.responses.JwtResponse;
import com.findapickle.backend.services.UsersService;
import com.findapickle.backend.utils.JwtTokenUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path="/users")
public class UsersController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsersService usersService;

    @PostMapping(path="/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        doAuthenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserEntity userDetails = usersRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException(authenticationRequest.getEmail(), "User"));
        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> register(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        try {
            UserEntity savedUser = usersService.save(token, user);
            doAuthenticate(user.getEmail(), user.getPassword());
            final UserEntity userDetails = usersRepository.findByEmail(savedUser.getEmail()).orElseThrow(() -> new EntityNotFoundException(user.getEmail(), "User"));
            final String accessToken = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(accessToken));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException("A user with the same email already exists");
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(
                    "Could not register the user. Please make sure that all required fields are present");
        } catch (Exception e) {
            throw new UnknownErrorException();
        }
    }

    private void doAuthenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        catch(Exception e){
            throw new Exception("Internal error");
        }
    }
}
