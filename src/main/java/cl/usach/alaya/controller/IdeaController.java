package cl.usach.alaya.controller;

import cl.usach.alaya.model.Idea;
import cl.usach.alaya.service.IdeaService;
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
@RequestMapping("/idea")
@Api(value = "idea", description = "Idea API", produces = "application/json")
public class IdeaController {
    @Autowired
    private IdeaService ideaService;

    @GetMapping
    @ApiOperation(value = "Get Ideas", notes = "Return all ideas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one idea at least")
    })
    public ResponseEntity getAllIdeas() {
        try {
            return new ResponseEntity<>(ideaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get one Idea", notes = "Return one idea")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one specific idea")
    })
    public ResponseEntity getIdea(@ApiParam(value = "The id of the idea to return")
                                  @PathVariable String id) {
        try {
            return new ResponseEntity<>(ideaService.getOneIdea(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/challenge")
    @ApiOperation(value = "Get Ideas of a challenge", notes = "Return all ideas of a challenge")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits all ideas of an specific challenge")
    })
    public ResponseEntity getAllIdeasByChallenge(@ApiParam(name = "id", value = "The id of the challenge")
                                                 @RequestParam("id") String id) {
        try {
            return new ResponseEntity<>(ideaService.getAllByChallenge(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "Get Ideas of a user", notes = "Return all created by a user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits all ideas of an specific user")
    })
    public ResponseEntity getAllIdeasByUser(@ApiParam(name = "id", value = "The id of the user")
                                            @RequestParam("id") String id) {
        try {
            return new ResponseEntity<>(ideaService.getAllByUser(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Idea", notes = "Create a idea")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful create of a idea")
    })
    public ResponseEntity createIdea(@Valid @RequestBody Idea idea) {
        try {
            return new ResponseEntity<>(ideaService.createIdea(idea), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update Idea", notes = "Update a idea")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful update of a idea")
    })
    public ResponseEntity updateComment(@ApiParam(value = "The id of the idea to return")
                                        @PathVariable String id,
                                        @RequestBody Idea idea) {
        try {
            return new ResponseEntity<>(ideaService.updateIdea(id, idea), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete Idea", notes = "Delete a idea")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete of a idea")
    })
    public ResponseEntity deleteIdea(@ApiParam(value = "The id of the idea to return")
                                     @PathVariable String id) {
        try {
            ideaService.deleteIdea(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/search")
    @ApiOperation(value = "Search Idea", notes = "Search a idea")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful search of an idea")
    })
    @ResponseBody
    public List<Idea> filter(@ApiParam(name = "search", value = "The query to execute")
                                 @RequestParam("search") String search) {
        return Lucene.searchIdea(search);
    }
}
