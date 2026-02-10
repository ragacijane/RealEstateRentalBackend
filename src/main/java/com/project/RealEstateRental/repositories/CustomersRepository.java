package com.project.RealEstateRental.repositories;
import com.project.RealEstateRental.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CustomersRepository extends JpaRepository<Customers,Integer> {

    @Query("SELECT COALESCE(MAX(c.idCustomer), 0) FROM Customers c")
    long findMaxIdCustomer();

    @Query(value = """
    SELECT * FROM customers c
    WHERE (:idCustomer IS NULL OR c.id_customer = :idCustomer)
      AND (:firstName IS NULL OR c.first_name ILIKE CONCAT('%', :firstName, '%'))
      AND (:secondName IS NULL OR c.second_name ILIKE CONCAT('%', :secondName, '%'))
      AND (:email IS NULL OR c.email ILIKE CONCAT('%', :email, '%'))
      AND (:phoneNumber IS NULL OR c.phone_number ILIKE CONCAT('%', :phoneNumber, '%'))
      AND (:clientType IS NULL OR c.client_type = :clientType)
""",
            nativeQuery = true)
    List<Customers> findByFilter(
            @Param("idCustomer") Integer idCustomer,
            @Param("firstName") String firstName,
            @Param("secondName") String secondName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("clientType") String clientType,
            Pageable pageable
    );

    @Query(value = """
    SELECT COUNT(*) FROM customers c
    WHERE (:idCustomer IS NULL OR c.id_customer = :idCustomer)
      AND (:firstName IS NULL OR c.first_name ILIKE CONCAT('%', :firstName, '%'))
      AND (:secondName IS NULL OR c.second_name ILIKE CONCAT('%', :secondName, '%'))
      AND (:email IS NULL OR c.email ILIKE CONCAT('%', :email, '%'))
      AND (:phoneNumber IS NULL OR c.phone_number ILIKE CONCAT('%', :phoneNumber, '%'))
      AND (:clientType IS NULL OR c.client_type = :clientType)
    """,
            nativeQuery = true)
    long countByFilter(
            @Param("idCustomer") Integer idCustomer,
            @Param("firstName") String firstName,
            @Param("secondName") String secondName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("clientType") String clientType
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE customers SET is_active = NOT is_active WHERE id_customer = :id", nativeQuery = true)
    int toggleActive(@Param("id") Integer id);

}
