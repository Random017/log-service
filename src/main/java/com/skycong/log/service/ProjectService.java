package com.skycong.log.service;

import com.skycong.log.kit.Result;
import com.skycong.log.model.ProjectModel;
import com.skycong.log.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ruanmingcong 2020.10.16 9:43
 */
@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    public Result add(ProjectModel model) {
        String name = model.getName();
        ProjectModel byName = projectRepository.findByName(name);
        if (byName != null) {
            return Result.ofErr("该项目名称已存在");
        }
        ProjectModel save = projectRepository.save(model);
        return Result.ofOk(save);
    }


    public Result update(ProjectModel model) {
        boolean b = projectRepository.existsById(model.getId());
        if (!b) {
            return Result.ofErr("记录不存在");
        }
        ProjectModel save = projectRepository.save(model);
        return Result.ofOk(save);
    }


    public void delete(ProjectModel model) {
        projectRepository.deleteById(model.getId());
    }

    public Result getAll() {
        return Result.ofOk(projectRepository.findAll());
    }
}
