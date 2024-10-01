package com.app.persistence.repository;

import com.app.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleCrudRepository extends CrudRepository<RoleEntity,Long> {
    List<RoleEntity> getRolesEntitiesByRoleEnumIn(List<String> roleNames);
}
