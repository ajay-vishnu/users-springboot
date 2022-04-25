package com.ajayvishnu.users.repositories;

import com.ajayvishnu.users.objects.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {
    @Query("select u from UserInfo u where u.isDeleted=false")
    List<UserInfo> findAllAndNotDeleted();

    @Query("select u from UserInfo u where u.isDeleted=false and u.username=?1")
    Optional<UserInfo> findByUsername(String username);
}
