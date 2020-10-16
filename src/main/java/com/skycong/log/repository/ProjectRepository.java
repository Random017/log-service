package com.skycong.log.repository;

import com.skycong.log.model.ProjectModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ruanmingcong 2020.10.16 17:30
 */
public interface ProjectRepository extends MongoRepository<ProjectModel, String> {


    ProjectModel findByName(String name);


}
