package cl.usach.alaya.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "User document", description = "User acepted by a company")
@Document(collection = "users")
public class User {
    @Id
    @Getter
    @ApiModelProperty(value = "The id of the user", required = false)
    private String _id;

    @Getter
    @Setter
    @NonNull
    @Indexed(unique=true)
    @ApiModelProperty(value = "The rut of the user", required = true)
    private String rut;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The name of the user", required = true)
    private String name;

    @Getter
    @Setter
    @Field("alt_name")
    @ApiModelProperty(value = "The alternative name of the user", required = false)
    private String altName;

    @Getter
    @Setter
    @Field("user_type")
    @NonNull
    @ApiModelProperty(value = "The type of the user", required = true)
    private String userType;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The username of the user", required = true)
    private String username;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The password of the user", required = true)
    private String password;

    @Getter
    @Setter
    @NonNull
    @Indexed(unique=true)
    @ApiModelProperty(value = "The mail of the user", required = true)
    private String mail;

    @Getter
    @Setter
    @Field("status")
    @NonNull
    @ApiModelProperty(value = "The status of the user", required = true)
    private String status;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("registered_at")
    @ApiModelProperty(value = "The date when the User was registered", required = false)
    private Date registeredDate;

    @Getter
    @Setter
    @NonNull
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("last_login")
    @ApiModelProperty(value = "The date of the last login", required = true)
    private Date lastLogin;


}
