package auth.dto.request;

import auth.application.doc.Examples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(example = Examples.CREATE_USER_REQUEST)
public class CreateUserDTO {

    @NotBlank
    @Size(max = 250)
    private String firstName;

    @NotBlank
    @Size(max = 250)
    private String lastName;

    @Size(max = 250)
    private String otherName;

    @NotBlank
    @Size(max = 250)
    private String userName;

    @Email
    @Size(max = 250)
    private String contactEmail;

    @Size(max = 20)
    private String contactPhonenumber;

    private String password;

}
