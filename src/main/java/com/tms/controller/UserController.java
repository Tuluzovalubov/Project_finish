package com.tms.controller;
import com.tms.model.UserInfo;
import com.tms.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
@Tag(name="Test teg",description = "This is our test tag description")
    @GetMapping
    public ResponseEntity <List <UserInfo>> getUsers() {
        List<UserInfo> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @Tag(name="Test teg",description = "This is our test tag description")
    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable Integer id) {
        UserInfo userInfo = userService.getUser(id);
        if (userInfo != null) {
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userInfo, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserInfo userInfo) {
        UserInfo userInfoSaved = userService.createUser(userInfo);
        UserInfo userInfoResult = userService.getUser(userInfoSaved.getId());
        if (userInfoResult != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserInfo userInfo) {
        userService.updateUser(userInfo);
        UserInfo userInfoUpdated = userService.getUser(userInfo.getId());
        if (userInfo.equals(userInfoUpdated)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
        @DeleteMapping("/{id}")
        public ResponseEntity deletUser (@PathVariable Integer id){
        UserInfo userInfoUpdated=userService.getUser(id);
            userService.deleteUserById(id);
            UserInfo userInfo = userService.getUser(id);
            if (userInfo == null&& userInfoUpdated !=null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }
    }
