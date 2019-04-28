package cl.usach.alaya.controller;

import cl.usach.alaya.model.User;
import cl.usach.alaya.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/user")
@Api(value = "user", description = "User API", produces = "application/json")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    @ApiOperation(value = "Create User", notes = "Register a user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful register of a user")
    })
    public ResponseEntity register(@Valid @RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/login")
    @ApiOperation(value = "Get User", notes = "Return user after login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits user when login is successful")
    })
    public ResponseEntity login(@ApiParam(name = "mail", value = "The mail of the user")
                                @RequestParam("mail") String mail,
                                @ApiParam(name = "pass", value = "The password of the user")
                                @RequestParam("pass") String pass) {
        User user = userService.verifyUser(mail, pass);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

}
