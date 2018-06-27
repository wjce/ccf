package com.wjc.ccf.web;

import com.wjc.ccf.MongoService;
import com.wjc.ccf.domain.Mongo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@RequestMapping(value = "/req_mongo")
@Controller
public class MongoController {

    @Autowired
    private MongoService mongoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody Mongo mongo){
        mongo.setCreateDate(DateUtils.addHours(new Date(), 8));
        mongoService.save(mongo);
        return "redirect:list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String findList(Model model, Pageable pageable){
        Page<Mongo> page = mongoService.findList(pageable);

        model.addAttribute("page", page);
        return "/mongo/mongo_list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Mongo mongo){
        mongoService.save(mongo);
        return "redirect:list";
    }

}
