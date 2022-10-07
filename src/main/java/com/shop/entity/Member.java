package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {
    }

    private Member(String name, String email, String password, String address, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String encoderPassword = passwordEncoder.encode(memberFormDto.getPassword());
        Member member = of(memberFormDto.getName(), memberFormDto.getEmail(), encoderPassword, memberFormDto.getAddress(), Role.USER);
        return member;
    }

    public static Member of(String name, String email, String password, String address, Role role) {
        return new Member(name, email, password, address, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return id != null && id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
