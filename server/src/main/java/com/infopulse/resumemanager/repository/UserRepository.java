package com.infopulse.resumemanager.repository;

import com.infopulse.resumemanager.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
//    @Query("")
//    void addRoleToUser(String username, String roleName);

    //List<User> findAllByUsername(String username, Pageable pageable);
//    User saveUser(User user);
//    Role saveRole(Role role);
//    void addRoleToUser(String username, String roleName);
//    User getUser(String username);
//    List<User> getUsersWithPageRequest(PageRequest pageRequest);
}
