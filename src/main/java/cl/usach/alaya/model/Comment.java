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
@ApiModel(value = "Comment document", description = "Comment uploaded by a collaborators")
@Document(collection = "comments")

public class Comment {
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
    @Field("idea_id")
    @NonNull
    @ApiModelProperty(value = "The id of the idea who refer the comment", required = true)
    private String ideaId;

    @Getter
    @Setter
    @NonNull
    @ApiModelProperty(value = "The content of the comment", required = true)
    private String text;

    @Getter
    @Setter
    @ApiModelProperty(value = "The votes of the comment", required = false)
    private int votes;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("created_at")
    @ApiModelProperty(value = "The date when the comment was created", required = false)
    private Date createDate;

    @Getter
    @Setter
    @JsonFormat(
            pattern="dd/MM/yyyy' a las 'HH:mm",
            timezone="America/Santiago")
    @Field("updated_at")
    @ApiModelProperty(value = "The date when the comment was update", required = false)
    private Date updateDate;


}
