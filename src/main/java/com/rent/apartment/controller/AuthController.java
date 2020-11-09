package com.rent.apartment.controller;


import com.rent.apartment.dto.AuthenticationResponse;
import com.rent.apartment.dto.LoginDto;
import com.rent.apartment.dto.UserDto;
import com.rent.apartment.response.SingleResult;
import com.rent.apartment.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
//@CrossOrigin(origins = ("*"))
public class AuthController {

    private final AuthService authService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<SingleResult<Long, UserDto>> signup(@RequestBody UserDto registerRequestDto) {
        return authService.signup(registerRequestDto);

    }

//    @RequestMapping(value = "accountVerification/{token}", method = RequestMethod.GET)
//    public SingleResult<Long, UserDto> verifyAccount(@PathVariable String token) {
//        return authService.verifyAccount(token);
//    }



    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<SingleResult<Long, AuthenticationResponse>> login(@RequestBody LoginDto loginRequest) {
        return authService.login(loginRequest);
    }

}
