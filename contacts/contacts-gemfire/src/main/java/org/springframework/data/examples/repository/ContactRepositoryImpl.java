package org.springframework.data.examples.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.gemfire.cache.query.SelectResults;

public class ContactRepositoryImpl implements CustomPagingRepository {
	
	@Autowired GemfireTemplate template;
	
    Log log = LogFactory.getLog(ContactRepositoryImpl.class);
	@Override
	public Page<Contact> findAll(Pageable pageable) {
		log.debug("called list with page = " + pageable.getPageNumber() + " and size = " + pageable.getPageSize() );
		long first = pageable.getOffset() + 1;
		long last = first + pageable.getPageSize();
		
	    SelectResults<Contact> results = template.find("select * from /Contact where id >= $1 and id <= $2", first,last);
		return results == null? null: new PageImpl<Contact>(results.asList(),pageable,results.size());
	}

}
