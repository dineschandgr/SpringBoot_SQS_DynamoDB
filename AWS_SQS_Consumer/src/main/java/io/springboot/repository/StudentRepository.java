package io.springboot.repository;

import org.springframework.stereotype.Repository;
import io.springboot.model.Student;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@Repository
@EnableScan
public interface StudentRepository extends CrudRepository<Student, String>{

}
