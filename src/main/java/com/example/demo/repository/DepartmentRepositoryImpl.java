package com.example.demo.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    //部署一覧を取得--------------------------------------------------------
    @Override
    public List<Department> getDepartmentList() {
        String sql = """
                SELECT 
                    id, name_jp, name_en
                FROM
                    departments
                ORDER BY name_jp DESC
                """;
        List<Map<String, Object>> list
        = jdbcTemplate.queryForList(sql);

        List<Department> result = new ArrayList<Department>();
        for (Map<String, Object> one : list) {
                Department department = new Department();
                department.setId((Long) one.get("id"));
                department.setNameJp((String) one.get("name_jp"));
                department.setNameEn((String) one.get("name_en"));
   
                result.add(department);
                }
            return result;
        }


    //部署検索結果を取得--------------------------------------------------------
    @Override
    public List<Department> getDepartmentSearch(String keyword) {
        String sql = """
                SELECT 
                    id, name_jp, name_en
                FROM
                    departments
                WHERE name_jp LIKE ?
                ORDER BY name_jp DESC
                """;
        String p = "%" + keyword + "%";
        
        List<Map<String, Object>> list
        = jdbcTemplate.queryForList(sql, p);

        List<Department> result = new ArrayList<Department>();
        for (Map<String, Object> one : list) {
                Department department = new Department();
                department.setId((Long) one.get("id"));
                department.setNameJp((String) one.get("name_jp"));
                department.setNameEn((String) one.get("name_en"));

                result.add(department);
                }
            return result;
        }


    //部署検索件数を取得--------------------------------------------------------
    @Override
    public Long getDepartmentSearchCount(String keyword) {
        String sql = """
                SELECT 
                    count(*)
                FROM
                    departments
                WHERE name_jp LIKE ?
                """;
        String p = "%" + keyword + "%";
        
        Long Count = jdbcTemplate.queryForObject(sql, Long.class, p);
        
        return Count;
        }


    //新規部署追加--------------------------------------------------------
    // 日本語名の重複チェック
    public boolean existsByNameJp(String name_jp) {
        String sql = """
                SELECT 
                    count(*)
                FROM
                    departments
                WHERE LOWER(name_jp) = LOWER(?)
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name_jp);
        return count != null && count > 0;
    }

    // 英語名の重複チェック
    public boolean existsByNameEn(String name_en) {
        String sql = """
                SELECT 
                    count(*)
                FROM
                    departments
                WHERE LOWER(name_en) = LOWER(?)
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name_en);
        return count != null && count > 0;
    }

    //新規登録
    @Override
    public void addDepartment(String name_jp, String name_en) {
        String sql = """
                INSERT INTO departments
                    (name_jp, name_en)
                VALUES
                    (?, ?)
                """;
        jdbcTemplate.update(sql, name_jp, name_en);
        }


    //編集・削除画面に表示する部署名の取得
    @Override
    public Department getDepartmentName(Long departmentsId) {
        String sql = """
                SELECT 
                    name_jp, name_en
                FROM
                    departments
                WHERE id = ?
                """;
        
        Map<String, Object> getname
        = jdbcTemplate.queryForMap(sql, departmentsId);

        Department department = new Department();
        department.setNameJp((String) getname.get("name_jp"));
        department.setNameEn((String) getname.get("name_en"));

        return department;
        }


    //部署名の編集機能
    @Override
    public void updateDepartment(Long departmentsId, String name_jp, String name_en) {
        String sql = """
                UPDATE
                    departments
                SET
                    name_jp = ?, name_en = ?
                WHERE
                    id = ?
             """;
            jdbcTemplate.update(sql, name_jp, name_en, departmentsId);
        }


    //部署名の削除機能
    @Override
    public void deleteDepartment(Long departmentsId) {
        String sql = """
                DELETE FROM
                    departments
                WHERE
                    id = ?
             """;
            jdbcTemplate.update(sql, departmentsId);
        }
}
