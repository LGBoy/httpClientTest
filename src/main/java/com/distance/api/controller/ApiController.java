package com.distance.api.controller;

import com.distance.common.utils.R;
import com.distance.common.utils.RequestFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 87796
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @RequestMapping("readResponseFile")
    public R readResponseFile(String uuid){
        return  RequestFile.responseReader(uuid);
    }
}
