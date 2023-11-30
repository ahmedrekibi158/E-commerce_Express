package com.are_commerce.ecommercewebsite.Repository;
import com.are_commerce.ecommercewebsite.Model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Long> {
    public DBUser findByUsername(String username);

    boolean existsByUsername(String username);
}