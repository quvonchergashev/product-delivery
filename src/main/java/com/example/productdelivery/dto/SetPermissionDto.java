package com.example.productdelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPermissionDto {
    private Long roleId;
    private List<Long> permissionsId;
}
