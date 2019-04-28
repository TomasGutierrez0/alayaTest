package cl.usach.alaya.controller;

import cl.usach.alaya.model.Challenge;
import cl.usach.alaya.service.ChallengeService;
import cl.usach.alaya.utils.Lucene;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/challenge")
@Api(value = "challenge", description = "Challenge API", produces = "application/json")
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    @ApiOperation(value = "Get Challenges", notes = "Return all challenges")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one challenge at least")
    })
    public ResponseEntity getAllChallenges() {
        try {
            return new ResponseEntity<>(challengeService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get one Challenge", notes = "Return one challenge")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one specific challenge")
    })
    public ResponseEntity getChallenge(@ApiParam(value = "The id of the challenge to return")
                                       @PathVariable String id) {
        try {
            return new ResponseEntity<>(challengeService.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/company/{id}")
    @ApiOperation(value = "Get all Challenges of a company", notes = "Return all Challenges of a company")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits all Challenges of a company")
    })
    public ResponseEntity getChallengesCompany(@ApiParam(value = "The id of the company")
                                       @PathVariable String id) {
        Iterable<Challenge> challenges = challengeService.getAllChallengesOfCompany(id);
        if (challenges == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(challenges);
    }

    @PostMapping
    @ApiOperation(value = "Create Challenge", notes = "Create a challenge")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful create of a challenge")
    })
    public ResponseEntity createChallenge(@Valid @RequestBody Challenge challenge,
                                          @ApiParam(name = "deadline", value = "Limit day of acceptance of Ideas")
                                          @RequestParam("deadline")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date deadline) {
        try {
            return new ResponseEntity<>(challengeService.create(challenge, deadline), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update Challenge", notes = "Update a challenge")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful update of a challenge")
    })
    public ResponseEntity updateChallenge(@ApiParam(value = "The id of the challenge to return") @PathVariable String id,
                                          @RequestBody Challenge challenge,
                                          @ApiParam(name = "deadline", value = "Limit day of acceptance of Ideas")
                                          @RequestParam("deadline")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date deadline) {
        try {
            return new ResponseEntity<>(challengeService.update(id, challenge, deadline), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete Challenge", notes = "Delete a challenge")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete of a challenge")
    })
    public ResponseEntity deleteChallenge(@ApiParam(value = "The id of the challenge to return")
                                          @PathVariable String id) {
        try {
            challengeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search")
    @ApiOperation(value = "Search Challenge", notes = "Search a challenge")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful search of an idea")
    })
    @ResponseBody
    public List<Challenge> filter(@ApiParam(name = "search", value = "The query to execute")
                                  @RequestParam("search") String search,
                                  @ApiParam(name = "category", value = "The query to execute")
                                  @RequestParam("category") String category) {
        return Lucene.searchChallenge(search, category);
    }
}
