//package dev.shopping.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "member")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, length = 50)
//    private String name;
//
//    @Builder
//    public Member(String name) {
//        this.name = name;
//    }
//}
