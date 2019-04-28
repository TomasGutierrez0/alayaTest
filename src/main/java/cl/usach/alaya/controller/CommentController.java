package cl.usach.alaya.controller;

import cl.usach.alaya.model.Comment;
import cl.usach.alaya.service.CommentService;
import cl.usach.alaya.utils.Lucene;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/comment")
@Api(value = "comment", description = "Comment API", produces = "application/json")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    @ApiOperation(value = "Get Comments", notes = "Return all comments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one comment at least")
    })
    public ResponseEntity getAllComments() {
        try {
            return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get one Comment", notes = "Return one comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one specific comment")
    })
    public ResponseEntity getComment(@ApiParam(value = "The id of the comment to return")
                                     @PathVariable String id) {
        try {
            return new ResponseEntity<>(commentService.getOneComment(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Comment", notes = "Create a comment")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful create of a comment")
    })
    public ResponseEntity createComment(@Valid @RequestBody Comment comment) {
        try {
            return new ResponseEntity<>(commentService.createComment(comment), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update Comment", notes = "Update a comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful update of a comment")
    })
    public ResponseEntity updateComment(@ApiParam(value = "The id of the comment to return")
                                        @PathVariable String id,
                                        @RequestBody Comment comment) {
        try {
            return new ResponseEntity<>(commentService.updateComment(id, comment), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete Comment", notes = "Delete a comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete of a comment")
    })
    public ResponseEntity deleteComment(@ApiParam(value = "The id of the comment to return")
                                        @PathVariable String id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value="/search")
    @ApiOperation(value = "Search Comment", notes = "Search a comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful search of an comment")
    })
    @ResponseBody
    public List<Comment> filter(@ApiParam(name = "search", value = "The query to execute")
                             @RequestParam("search") String search) {
        return Lucene.searchComment(search);
    }
}
