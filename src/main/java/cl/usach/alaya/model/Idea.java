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
@ApiModel(value = "Idea document", description = "Idea uploaded by a collaborators")
@Document(collection = "ideas")

public class Idea {
    @Id
    @Getter
    @ApiModelProperty(value = "The id of the comment", required = false)
    private String _id;

    @Getter
    @Setter
    @Field("user_id")
    @NonNull
    @ApiModelProperty(value = "The id of the collaborator who upload the comment", required = true)
    private String userId;

    @Getter
    @Setter
    @Field("challenge_id")
    @NonNull
    @ApiModelProperty(value = "The id of the challenge who refer the idea", required = true)
    private String challengeId;

    @Getter
    @Setter
    @Field("text")
    @NonNull
    @ApiModelProperty(value = "The content of the idea", required = true)
    private String text;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The title of the idea", required = true)
    private String title;

    @Getter
    @Setter
    @Field("votes")
    @ApiModelProperty(value = "The votes of the idea", required = false)
    private int votes;

    @Getter
    @Setter
    @Field("status")
    @NonNull
    @ApiModelProperty(value = "The status of the idea", required = false)
    private String status;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("created_at")
    @ApiModelProperty(value = "The date when the idea was created", required = false)
    private Date createDate;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("updated_at")
    @ApiModelProperty(value = "The date when the idea was update", required = false)
    private Date updateDate;

}
