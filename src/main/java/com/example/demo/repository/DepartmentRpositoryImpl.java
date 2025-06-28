package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DepartmentRpositoryImpl implements DepartmentRepository {
    
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Department> userList(Long userId) {
        String sql ="""
                SELECT
                        d.id, d.name_jp, d.name_en
                FROM
                        departments
                WHERE
                        d.id <> ?
                """;

        List<Map<String, Object>> list
                        = jdbcTemplate.queryForList(sql, userId);
        
        List<Department> result = new ArrayList<Department>();
        for (Map<String, Object> one : list) {
                User user = new User();
    user.setId((Long) one.get("id"));
    user.setName((String) one.get("name_jp"));
    user.setName((String) one.get("name_en"));

                result.add(user);
        }
        return null;
    }

}
