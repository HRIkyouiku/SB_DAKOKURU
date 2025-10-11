package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	//--カスタムクエリメソッドの例--

	/*
	 *部署名で部署を検索する（完全一致）
	 */
	List<Department> findByNameJp(String departmentName);

	/**
	 * 部署名で部分一致検索を行います。
	 * 例: "部" を含む部署を検索
	 */
	List<Department> findByNameJpContaining(String keyword);

	// 部署名（英語）で部分一致検索
	List<Department> findByNameEnContaining(String keyword);

	/**
	 * 特定の文字列で始まる部署名を検索します。
	 * SQLの LIKE 'keyword%' に相当します。
	 */
	List<Department> findByNameJpStartingWith(String prefix);

	/**
	 * 特定の文字列で終わる部署名を検索します。
	 * SQLの LIKE '%keyword' に相当します。
	 */
	List<Department> findByNameJpEndingWith(String suffix);

	boolean existsByNameJp(String nameJp);
	
	boolean existsByNameEn(String nameEn);

	List<Department> findByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCase(String nameJp, String nameEn);

	boolean existsByNameJpAndIdNot(String nameJp, Long id);

	boolean existsByNameEnAndIdNot(String nameEn, Long id);

}
