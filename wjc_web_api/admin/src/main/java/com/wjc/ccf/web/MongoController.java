package com.wjc.ccf.web;

import com.wjc.ccf.MongoService;
import com.wjc.ccf.domain.Mongo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping(value = "/req_mongo")
@Controller
public class MongoController {
    Logger logger = LoggerFactory.getLogger(MongoController.class);

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

    @RequestMapping(value = "/find_name_and_age", method = RequestMethod.GET)
    public String findNameAndAge(Integer age, String name){
        List<Mongo> mongoList = mongoService.findByAgeAndName(age, name);
        System.out.println(mongoList.size());
        return "redirect:list";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Mongo mongo){
        Mongo oldMongo = mongoService.findOne(mongo.getId());
        mongo.setCreateDate(oldMongo.getCreateDate());
        mongoService.save(mongo);
        return "redirect:list";
    }

    /**
     * 通过持久化修改
     * @return
     */
    @RequestMapping(value = "/update2", method = RequestMethod.POST)
    public String update2(@RequestBody Mongo mongo){

        mongo = mongoService.update(mongo);
        logger.debug(mongo.toString());
        return "redirect:list";
    }

    @RequestMapping(value = "/find_mongo", method = RequestMethod.GET)
    public @ResponseBody String findMongo(Long id){
        Mongo mongo = mongoService.findOneByTemplate(id);
        logger.debug(mongo.toString());
        return mongo.toString();
    }

    @RequestMapping(value = "/find_mongo_list", method = RequestMethod.GET)
    public String findMongoList(){
        List<Mongo> mongoList = mongoService.findMongoList();
        return "redirect:list";
    }

    @RequestMapping(value = "/save_mongo", method = RequestMethod.POST)
    public String saveMongo(@RequestBody Mongo mongo){
        mongo.setCreateDate(DateUtils.addHours(new Date(), 8));
        mongoService.saveMongo(mongo);
        return "redirect:list";
    }

    @RequestMapping(value = "/del_mongo", method = RequestMethod.GET)
    public String delMongo(Long id){
        mongoService.delMongo(id);
        return "redirect:list";
    }

    @RequestMapping(value = "/update_mongo", method = RequestMethod.POST)
    public String updateMongo(@RequestBody Mongo mongo){
        mongoService.updateMongo(mongo);
        return "redirect:list";
    }

    @RequestMapping(value = "/update_first_mongo", method = RequestMethod.POST)
    public String updateFirstMongo(@RequestBody Mongo mongo){
        mongoService.updateFirstMongo(mongo);
        return "redirect:list";
    }

    @RequestMapping(value = "/update_first_mongo_age", method = RequestMethod.POST)
    public String updateFirstMongoAge(@RequestBody Mongo mongo){
        mongoService.updateFirstMongoAge(mongo);
        return "redirect:list";
    }

    @RequestMapping(value = "/find_stream", method = RequestMethod.GET)
    public String findStream(){
//        mongoService.findStream();
        return "/";
    }
}
