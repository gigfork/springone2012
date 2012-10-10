/*
 * Copyright (c) 2012 by the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.examples.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.aop.support.AopUtils;
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

/**
 * 
 * @author David Turanski
 *
 */
@RequestMapping("/contacts")
@Controller
public class ContactController {

	@Autowired
	ContactRepository contactRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Contact contact, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, contact);
			return "contacts/create";
		}
		uiModel.asMap().clear();
		contact.setId(contactRepository.count() + 1);
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
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "first", required = false) String firstname,
			@RequestParam(value = "last", required = false) String lastname, Model uiModel) {

		int sizeNum = size == null ? 10 : size.intValue();

		Iterable<Contact> contacts = null;

		if (StringUtils.hasText(firstname)) {
			contacts = contactRepository.findByFirstname(firstname);
		} else if (StringUtils.hasText(lastname)) {
			contacts = contactRepository.findByLastnameStartsWith(lastname);
		} else {
			int pageNum = page == null ? 0 : page.intValue() - 1;
			contacts = contactRepository.findAll(new org.springframework.data.domain.PageRequest(pageNum, sizeNum))
					.getContent();
		}
		float nrOfPages = (float) contactRepository.count() / sizeNum;
		uiModel.addAttribute("contacts", contacts);
		uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
				: nrOfPages));
		uiModel.addAttribute("repository", "using repository: " + AopUtils.getTargetClass(contactRepository).getName());
		return "contacts/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Contact contact, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
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
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Contact contact = contactRepository.findOne(id);
		contactRepository.delete(contact);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
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
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
