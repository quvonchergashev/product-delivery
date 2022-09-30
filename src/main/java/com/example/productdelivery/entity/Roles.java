package com.example.productdelivery.entity;

import com.example.productdelivery.consts.RoleName;
import com.example.productdelivery.entity.template.AbsLongEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles extends AbsLongEntity implements GrantedAuthority {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;



    @Override
    public String getAuthority() {
        return String.valueOf(this.roleName);
    }
}
