package com.example.demo;

import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() throws UnmarshallingException, XMLParserException {
        ModelAndView map = new ModelAndView("index");
        map.addObject("groups");
        return map;
    }
}
