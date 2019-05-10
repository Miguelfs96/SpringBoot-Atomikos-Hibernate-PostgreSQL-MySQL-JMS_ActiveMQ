package com.miguel.distibuteddatabases.controller;

import com.miguel.distibuteddatabases.model.Direccion;
import com.miguel.distibuteddatabases.model.Persona;
import com.miguel.distibuteddatabases.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private InsertService insertService;

    @RequestMapping(value = {"/"})
    public ModelAndView showAll(){
        ModelAndView mav = new ModelAndView("showall");
        mav.addObject("personas", insertService.mostrarPersona());
        mav.addObject("direcciones", insertService.mostrarDireccion());
        return mav;
    }

    @RequestMapping(value = {"/process"})
    public ModelAndView insert(@ModelAttribute("persona") Persona p, @ModelAttribute("direccion") Direccion d){
        if ((d.getCalle() != "" && d.getCalle() != null) && (p.getNombre() != "" && p.getNombre() != null))
            insertService.save(p, d);
            else
                if ((d.getCalle() == "" || d.getCalle() == null)  && p.getNombre() != "" && p.getNombre() != null)
                    insertService.save(p);
                else
                if (d.getCalle() != "" && d.getCalle() != null && (p.getNombre() == "" ||  p.getNombre() == null))
                    insertService.save(d);

        ModelAndView mav = new ModelAndView("showall");
        mav.addObject("personas", insertService.mostrarPersona());
        mav.addObject("direcciones", insertService.mostrarDireccion());
        return mav;
    }

    @RequestMapping(value = {"/insert/user"})
    public ModelAndView setData () {
        ModelAndView mav = new ModelAndView("insertform");
        mav.addObject("persona", new Persona());
        mav.addObject("direccion", new Direccion());
        return mav;
    }

    @RequestMapping(value = {"/edit/persona"})
    public ModelAndView edit (@RequestParam("personaid") long id){
        ModelAndView mav = new ModelAndView("editpers");
        mav.addObject("persona", insertService.edit(id));
        return mav;
    }

    @RequestMapping(value = {"/edit/direccion"})
    public ModelAndView editDir (@RequestParam("dirid") long id){
        ModelAndView mav = new ModelAndView("editdir");
        mav.addObject("direccion", insertService.editDir(id));
        return mav;
    }

    @RequestMapping(value = {"/delete/persona"})
    public ModelAndView deletePers(@RequestParam("personaid") long id){
        insertService.deletePers(id);
        ModelAndView mav = new ModelAndView("showall");
        mav.addObject("personas", insertService.mostrarPersona());
        mav.addObject("direcciones", insertService.mostrarDireccion());
        return mav;
    }

    @RequestMapping(value = {"/delete/direccion"})
    public ModelAndView deleteDir(@RequestParam("dirid") long id){
        insertService.deleteDir(id);
        ModelAndView mav = new ModelAndView("showall");
        mav.addObject("personas", insertService.mostrarPersona());
        mav.addObject("direcciones", insertService.mostrarDireccion());
        return mav;
    }
}
