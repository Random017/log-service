package com.skycong.log.controller;

import com.skycong.log.model.ProjectModel;
import com.skycong.log.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanmingcong 2020.10.16 9:41
 */
@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping
    void post(@RequestBody ProjectModel model) {
        projectService.add(model);
    }


}
