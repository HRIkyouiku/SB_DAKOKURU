package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Name;

public interface NameRepository extends JpaRepository<Name, Long> {

    Name findByUserId(Long userId);

    @Query("""
                SELECT n.userId FROM Name n WHERE
                n.fnJp LIKE :keyword OR n.fnJpHira LIKE :keyword OR n.fnJpKata LIKE :keyword OR n.fnEn LIKE :keyword OR
                n.lnJp LIKE :keyword OR n.lnJpHira LIKE :keyword OR n.lnJpKata LIKE :keyword OR n.lnEn LIKE :keyword OR
                n.olnJp LIKE :keyword OR n.olnJpHira LIKE :keyword OR n.olnJpKata LIKE :keyword OR n.olnEn LIKE :keyword OR
                n.mnJp LIKE :keyword OR n.mnJpHira LIKE :keyword OR n.mnJpKata LIKE :keyword OR n.mnEn LIKE :keyword
            """)
    List<Long> searchByKeyword(@Param("keyword") String keyword);
}
