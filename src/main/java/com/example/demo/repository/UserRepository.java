package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
            "name"
    })
    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    User findByEmail(String email);

    User findByEmployeeNo(Long employeeNo);

    // 【ユーザーごと勤怠一覧】 
    
    // 検索機能

    // ２.名前あり＋部署なし　の処理
    // ユーザー名のみで検索(fnJpまたはlnJpでのあいまい検索)
    @Query("SELECT u FROM User u JOIN u.name n " + "WHERE n.fnJp LIKE %:name% OR n.lnJp LIKE %:name%")
    List<User> findByNameLike(@Param("name") String name);

    // ３.名前なし＋部署あり
    // 部署IDで検索
    List<User> findByDepartments_Id(Long departmentId);
    
    // ４.名前あり＋部署あり
    // ユーザー名(fnJpまたはlnJpでのあいまい検索)と部署IDで検索
    @Query("SELECT u FROM User u JOIN u.name n JOIN u.departments d " +
        "WHERE (n.fnJp LIKE %:name% OR n.lnJp LIKE %:name%) " +
        "AND d.id = :departmentId")
    List<User> findByNameAndDepartment(@Param("name") String name, @Param("departmentId") Long departmentId);
}
