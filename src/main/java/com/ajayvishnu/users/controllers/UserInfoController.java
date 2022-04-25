package com.ajayvishnu.users.controllers;


import com.ajayvishnu.users.json.UserInfoJson;
import com.ajayvishnu.users.objects.UserInfo;
import com.ajayvishnu.users.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public List<UserInfo> getAllUserInfo()  {
        return userInfoService.getAllUserInfo();
    }

    @GetMapping(path = "{username}")
    public UserInfo getUserInfo(@PathVariable("username") String username)    {
        return userInfoService.getUserInfoByUsername(username).orElseThrow(() -> new IllegalStateException(username + " does not exist"));
    }

    @PostMapping
    public void createNewUser(@RequestBody UserInfoJson userInfo) {
        userInfoService.addNewUser(userInfo);
    }

    @DeleteMapping(path = "{username}")
    public void deleteUserInfo(@PathVariable("username") String username,
                               @RequestParam String deletedBy)  {
        userInfoService.deleteUserInfo(username, deletedBy);
    }

    @PutMapping(path = "{username}")
    public void updateUserInfo(@RequestBody UserInfoJson userInfoJson)  {
        userInfoService.updateUserInfo(userInfoJson);
    }
}
