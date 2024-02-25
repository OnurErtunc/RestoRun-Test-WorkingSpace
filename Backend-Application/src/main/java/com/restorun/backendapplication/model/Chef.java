package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Chef extends Employee {

    @Column
    private String password;

    public Chef() {
        super();
        this.role = Role.CHEF;
    }

}
