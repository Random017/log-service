package com.skycong.log.service;

import com.skycong.log.model.ProjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ruanmingcong 2020.10.16 9:43
 */
@Service
public class ProjectService {


    @Autowired
    private MongoTemplate mongoTemplate;


    public void add(ProjectModel model) {
        ProjectModel insert = mongoTemplate.insert(model, ProjectModel.COLLECTION_NAME);
    }
}
