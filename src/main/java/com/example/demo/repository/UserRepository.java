package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
            "name"
    })
    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    User findByEmail(String email);

    User findByEmployeeNo(Integer employeeNo);

    @Query(value = """
                 SELECT
            u.id AS userId,
            n.fn_jp AS fnJp,
            n.ln_jp AS lnJp,
            d.name_jp AS departmentName,
            JSON_ARRAYAGG(
                JSON_OBJECT(
                    'date', t.date,
                    'timestamps', COALESCE((
                        SELECT JSON_ARRAYAGG(
                            JSON_OBJECT(
                                'time', t2.time,
                                'work_place_id', t2.work_place_id
                            )
                        )
                        FROM timestamps t2
                        WHERE t2.user_id = u.id
                        AND t2.date = t.date
                    ), JSON_ARRAY())
                )
            ) AS dailyTimestamps
                 FROM users u
                 JOIN names n ON u.id = n.user_id
                 JOIN department_affiliations da ON u.id = da.user_id
                 JOIN departments d ON da.department_id = d.id
                 LEFT JOIN timestamps t ON u.id = t.user_id
                 WHERE u.id IN (:userIds)
                   AND t.date BETWEEN :startDate AND :endDate
                 GROUP BY u.id, n.fn_jp, n.ln_jp, d.name_jp;
                 """, nativeQuery = true)
    List<Object[]> findDailyTimestamps(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
