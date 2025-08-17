package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "部署名を入力してください。")
	@Size(min = 0, max = 255, message = "部署名は1文字以上、255文字以内で入力してください。")
	@Column(name = "name_jp")
	private String nameJp;

	@NotBlank(message = "部署名（英語）を入力してください。")
	@Size(min = 0, max = 255, message = "部署名（英語）は1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "^[a-zA-Z0-9\\s-_＿]*$", message = "部署名（英語）は半角英数字で入力してください。")
	@Column(name = "name_en")
	private String nameEn;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToMany(mappedBy = "departments")
    private Set<User> users = new HashSet<>();
}
