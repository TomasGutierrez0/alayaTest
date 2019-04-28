package cl.usach.alaya.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Challenge document", description = "Challenge uploaded by a company")
@Document(collection = "challenges")
public class Challenge {
    @Id
    @Getter
    @ApiModelProperty(value = "The id of the challenge", required = false)
    private String _id;

    @Getter
    @Setter
    @Field("user_id")
    @NonNull
    @ApiModelProperty(value = "The id of the company who upload the challenge", required = true)
    private String userId;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The content of the challenge", required = true)
    private String text;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("created_at")
    @ApiModelProperty(value = "The date when the challenge was created", required = false)
    private Date createDate;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("updated_at")
    @ApiModelProperty(value = "The date when the challenge was update", required = false)
    private Date updateDate;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The title of the challenge", required = true)
    private String title;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The area of interest of the challenge", required = true)
    private String interestArea;

    @Getter
    @Setter
    @NonNull
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @ApiModelProperty(value = "The deadline of acceptance of ideas", required = true)
    private Date deadline;
}
