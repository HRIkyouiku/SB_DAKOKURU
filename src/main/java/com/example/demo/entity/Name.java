package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "names")
@Getter
@Setter
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "fn_jp")
    private String fnJp;

    @Column(name = "fn_jp_hira")
    private String fnJpHira;

    @Column(name = "fn_jp_kata")
    private String fnJpKata;

    @Column(name = "fn_en")
    private String fnEn;

    @Column(name = "ln_jp")
    private String lnJp;

    @Column(name = "ln_jp_hira")
    private String lnJpHira;

    @Column(name = "ln_jp_kata")
    private String lnJpKata;

    @Column(name = "ln_en")
    private String lnEn;

    @Column(name = "oln_jp")
    private String olnJp;

    @Column(name = "oln_jp_hira")
    private String olnJpHira;

    @Column(name = "oln_jp_kata")
    private String olnJpKata;

    @Column(name = "oln_en")
    private String olnEn;

    @Column(name = "mn_jp")
    private String mnJp;

    @Column(name = "mn_jp_hira")
    private String mnJpHira;

    @Column(name = "mn_jp_kata")
    private String mnJpKata;

    @Column(name = "mn_en")
    private String mnEn;

    @Column(name = "english_notation")
    private Boolean englishNotation;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
