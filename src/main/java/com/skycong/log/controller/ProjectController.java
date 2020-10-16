package com.skycong.log.controller;

import com.skycong.log.kit.Result;
import com.skycong.log.model.ProjectModel;
import com.skycong.log.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ruanmingcong 2020.10.16 9:41
 */
@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("all")
    Result getAll() {
        return projectService.getAll();
    }

    @PostMapping
    Result post(@RequestBody ProjectModel model) {
        return projectService.add(model);
    }

    @PutMapping
    Result put(@RequestBody ProjectModel model) {
        return projectService.update(model);
    }

    @DeleteMapping
    Result delete(@RequestBody ProjectModel model) {
        projectService.delete(model);
        return Result.ofOk();
    }


}
