package com.example.productdelivery.entity;

import com.example.productdelivery.entity.template.AbsLongEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles extends AbsLongEntity implements GrantedAuthority {

    @Column(unique = true)
    private String name;

    //    @EqualsAndHashCode.Exclude
    @ManyToMany(targetEntity = Permission.class, fetch = FetchType.EAGER)
    @ToString.Exclude
 /*   @JoinTable(name = "roles_permissions",
            joinColumns = { @JoinColumn(name = "fk_roles") },
            inverseJoinColumns = { @JoinColumn(name = "fk_permission") })*/
    private List<Permission> permissions;

    @Override
    public String getAuthority() {
        return String.valueOf(this.permissions);
    }
}
