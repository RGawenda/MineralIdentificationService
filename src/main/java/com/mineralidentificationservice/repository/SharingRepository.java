package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.Sharing;
import com.mineralidentificationservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharingRepository extends JpaRepository<Sharing, Long> {
    @Query("SELECT s.userFrom.username FROM Sharing s WHERE s.userTo.id = :userIdTo")
    List<String> findUsernamesSharingWithUserTo(@Param("userIdTo") Long userIdTo);

    @Query("SELECT s.userTo.username FROM Sharing s WHERE s.userFrom.id = :userIdFrom")
    List<String> findUsernamesSharingWithUserFrom(@Param("userIdFrom") Long userIdFrom);

    void deleteSharingByUserFromAndUserTo(UserAccount userFrom, UserAccount userTo);
}
