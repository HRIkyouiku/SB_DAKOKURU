package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentRepository departmentRepository;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll(); //すべての部署を取得
	}

	@Override
	public Optional<Department> findDepartmentById(Long id) {
		return departmentRepository.findById(id); //IDで部署を取得

	}

	@Override
	public Department createDepartment(Department department) {
		// 部署名（日本語）の重複チェック
		if (departmentRepository.existsByNameJp(department.getNameJp())) {
			throw new IllegalArgumentException("部署名は既に存在しています。");
		}
		// 部署名（英語）の重複チェック
		if (departmentRepository.existsByNameEn(department.getNameEn())) {
			throw new IllegalArgumentException("部署名（英語）は既に存在しています。"); // メッセージ文言を追加
		}

		department.setCreatedAt(LocalDateTime.now());
		department.setUpdatedAt(LocalDateTime.now());
		return departmentRepository.save(department);
	}

	@Override
	public Department updateDepartment(Long id, Department department) {
		// 1. 更新対象の部署をデータベースから取得
		Department existingDepartment = departmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("指定された部署が見つかりません。"));

		// 2. 部署名の重複チェックを改善
		// 新しい部署名（日本語）が、既存の部署名（日本語）と異なる場合のみ重複チェックを行う
		if (!existingDepartment.getNameJp().equals(department.getNameJp())) {
			if (departmentRepository.existsByNameJp(department.getNameJp())) {
				throw new IllegalArgumentException("部署名 '" + department.getNameJp() + "' は既に存在します。");
			}
		}

		if (!existingDepartment.getNameEn().equals(department.getNameEn())) {
			if (departmentRepository.existsByNameEn(department.getNameEn())) {
				throw new IllegalArgumentException("部署名（英語）は既に存在しています。");
			}
		}
		// 3. 取得した既存のエンティティを更新
		existingDepartment.setNameJp(department.getNameJp());
		existingDepartment.setNameEn(department.getNameEn());
		// updated_at の自動更新が効かない場合はここで手動設定
		// existingDepartment.setUpdatedAt(LocalDateTime.now()); 

		return departmentRepository.save(existingDepartment); // 変更をデータベースに保存
	}

	@Override
	public void deleteDepartment(Long id) {
		// 削除対象が存在するか確認（任意だが推奨）
		if (!departmentRepository.existsById(id)) {
			throw new IllegalArgumentException("指定されたIDの部署が見つかりません: " + id);
		}
		departmentRepository.deleteById(id); // JpaRepositoryのdeleteById()を利用
	}

	@Override
	public List<Department> searchDepartments(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return departmentRepository.findAll(); // キーワードがない場合は全件取得
		}
		// 日本語名または英語名で検索
		return departmentRepository.findByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCase(keyword, keyword);
	}

	@Override
	public Optional<Department> findById(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		return Optional.empty();
	}

}
