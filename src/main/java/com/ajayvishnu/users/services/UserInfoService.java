package com.ajayvishnu.users.services;

import com.ajayvishnu.users.json.UserInfoJson;
import com.ajayvishnu.users.objects.UserInfo;
import com.ajayvishnu.users.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<UserInfo> getAllUserInfo()  {
        return userInfoRepository.findAllAndNotDeleted();
    }

    public Optional<UserInfo> getUserInfoByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    public void addNewUser(UserInfoJson userInfoJson) {
        if (userInfoJson.getCreatedBy() != null && userInfoJson.getCreatedBy().length() > 0)    {
            Optional<UserInfo> userInfoOptional =  userInfoRepository.findByUsername(userInfoJson.getUsername());
            if (userInfoOptional.isPresent()) {
                throw new IllegalStateException("User with username " + userInfoJson.getUsername() + " already exists.");
            }
            UserInfo userInfo = new UserInfo(
                    userInfoJson.getUsername(),
                    userInfoJson.getFirstname(),
                    userInfoJson.getMiddleName(),
                    userInfoJson.getLastName(),
                    userInfoJson.getAddress(),
                    userInfoJson.getPhone(),
                    userInfoJson.getEmail(),
                    userInfoJson.getCreatedBy()
            );
            userInfoRepository.save(userInfo);
        }
        else    {
            throw new IllegalStateException("Must mention the createdBy parameter to update the database.");
        }
    }

    @Transactional
    public void updateUserInfo(UserInfoJson userInfoJson)   {
        if (userInfoJson.getCreatedBy() != null && userInfoJson.getCreatedBy().length() > 0) {
            boolean flag = false;
            UserInfo userInfo = userInfoRepository.findByUsername(userInfoJson.getUsername()).orElseThrow(() -> new IllegalStateException("No user with username " + userInfoJson.getUsername() + " found."));
            if (userInfoJson.getUsername() != null && !Objects.equals(userInfoJson.getNewUsername(), userInfo.getUsername()))  {
                userInfo.setUsername(userInfoJson.getNewUsername());
                flag = true;
            }
            if (userInfoJson.getAddress() != null && !Objects.equals(userInfoJson.getAddress(), userInfo.getAddress())) {
                userInfo.setAddress(userInfoJson.getAddress());
                flag = true;
            }
            if (userInfoJson.getEmail() != null && !Objects.equals(userInfoJson.getEmail(), userInfo.getEmail())) {
                userInfo.setEmail(userInfoJson.getEmail());
                flag = true;
            }
            if (userInfoJson.getPhone() != null && !Objects.equals(userInfoJson.getPhone(), userInfo.getPhone())) {
                userInfo.setPhone(userInfoJson.getPhone());
                flag = true;
            }
            if (flag) {
                userInfo.setUpdatedBy(userInfoJson.getCreatedBy());
                userInfo.setUpdatedAt(LocalDateTime.now());
            }
        }
        else    {
            throw new IllegalStateException("Must mention the updatedBy parameter to update the database.");
        }
    }

    @Transactional
    public void deleteUserInfo(String username, String deletedBy)   {
        if (deletedBy != null && deletedBy.length() > 0) {
            UserInfo userInfo = userInfoRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User with username " + username + " does not exist"));
            userInfo.setUpdatedAt(LocalDateTime.now());
            userInfo.setUpdatedBy(deletedBy);
            userInfo.setDeleted(true);
        }
        else    {
            throw new IllegalStateException("Must mention the deletedBy parameter to update the database.");
        }
    }
}
