package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Override
    public void flush() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public <S extends Department> S saveAndFlush(S entity) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Department> entities) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void deleteAllInBatch() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public Department getOne(Long id) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Department getById(Long id) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Department getReferenceById(Long id) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> List<S> findAll(Example<S> example) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> List<S> findAll(Example<S> example, Sort sort) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> List<S> saveAll(Iterable<S> entities) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public List<Department> findAll() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public List<Department> findAllById(Iterable<Long> ids) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> S save(S entity) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Optional<Department> findById(Long id) {
        // TODO 自動生成されたメソッド・スタブ
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public long count() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void delete(Department entity) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void deleteAll(Iterable<? extends Department> entities) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void deleteAll() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public List<Department> findAll(Sort sort) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> Optional<S> findOne(Example<S> example) {
        // TODO 自動生成されたメソッド・スタブ
        return Optional.empty();
    }

    @Override
    public <S extends Department> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <S extends Department> long count(Example<S> example) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    @Override
    public <S extends Department> boolean exists(Example<S> example) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public <S extends Department, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public User findByName(String name) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
