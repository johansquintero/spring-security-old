package com.app;

import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.repository.IRoleCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication{
    @Autowired
    private IRoleCrudRepository roleCrudRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
//			//CREATE permissions
//            PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
//            PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
//            PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
//            PermissionEntity refactorPermission = PermissionEntity.builder().name("REFACTOR").build();
//            PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();
//
//            //CREATE ROLES
//            RoleEntity adminRole = RoleEntity.builder()
//                    .roleEnum(RoleEnum.ADMIN)
//                    .permissionEntities(List.of(createPermission, readPermission, updatePermission, deletePermission)).build();
//
//            RoleEntity userRole = RoleEntity.builder()
//                    .roleEnum(RoleEnum.USER)
//                    .permissionEntities(List.of(createPermission, readPermission)).build();
//            RoleEntity invitedRole = RoleEntity.builder()
//                    .roleEnum(RoleEnum.INVITED)
//                    .permissionEntities(List.of(readPermission)).build();
//            RoleEntity devRole = RoleEntity.builder()
//                    .roleEnum(RoleEnum.DEVELOPER)
//                    .permissionEntities(List.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission)).build();
//
//            List<RoleEntity> roleEntityList = List.of(adminRole,devRole,invitedRole,userRole);
//            this.roleCrudRepository.saveAll(roleEntityList);
        };
	}
}
