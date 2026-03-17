package dev.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    @NotBlank(message = "이름은 필수로 입력해야 합니다.")
    private String name;
}
