package com.javalord.app.role;

import com.javalord.app.common.BaseEntity;
import com.javalord.app.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ROLES")
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
