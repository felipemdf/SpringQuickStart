package com.felipemdf.SpringQuickStart.repository;

import com.felipemdf.SpringQuickStart.model.Role;
import com.felipemdf.SpringQuickStart.model.enums.RoleEnum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByName(RoleEnum name);
}
