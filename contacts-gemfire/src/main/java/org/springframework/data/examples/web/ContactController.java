package org.springframework.data.examples.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.examples.repository.ContactRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/contacts")
@Controller
public class ContactController {

	@Autowired
    ContactRepository contactRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(Contact contact, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, contact);
            return "contacts/create";
        }
        uiModel.asMap().clear();
        
        contact.setId(contactRepository.count()+1);
        
        contactRepository.save(contact);
        return "redirect:/contacts/" + encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Contact());
        return "contacts/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contact", contactRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "contacts/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "first", required = false) String firstname, @RequestParam(value = "last", required = false) String lastname, Model uiModel) {
        
         if (StringUtils.hasText(firstname)) {
        	 uiModel.addAttribute("contacts", contactRepository.findByFirstname(firstname));
         } else if (StringUtils.hasText(lastname)) {
        	 uiModel.addAttribute("contacts", contactRepository.findByLastnameStartsWith(lastname));
         } else {
        	 uiModel.addAttribute("contacts", contactRepository.findAll());
         }
        
        return "contacts/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(Contact contact, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, contact);
            return "contacts/update";
        }
        uiModel.asMap().clear();
        contactRepository.save(contact);
        return "redirect:/contacts/" + encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, contactRepository.findOne(id));
        return "contacts/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, Model uiModel) {
        Contact contact = contactRepository.findOne(id);
        contactRepository.delete(contact);
        uiModel.asMap().clear();
   
        return "redirect:/contacts";
    }

	void populateEditForm(Model uiModel, Contact contact) {
        uiModel.addAttribute("contact", contact);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
