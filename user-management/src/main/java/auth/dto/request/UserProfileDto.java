package auth.dto.request;

import auth.application.doc.Examples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(example = Examples.UPDATE_PROFILE_REQUEST)
public class UserProfileDto {

    @NotBlank
    @Size(max = 250)
    private String firstName;

    @NotBlank
    @Size(max = 250)
    private String lastName;

    @Size(max = 250)
    private String otherName;

    @Email
    @Size(max = 250)
    private String contactEmail;

    @Size(max = 20)
    private String contactPhonenumber;
}
