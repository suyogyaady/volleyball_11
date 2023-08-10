package com.system.volleyball.Service.Impl;

import com.system.volleyball.Pojo.ContactPojo;
import com.system.volleyball.Repo.ContactRepo;
import com.system.volleyball.Service.ContactService;
import com.system.volleyball.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepo contactRepo;

    @Override
    public String save(ContactPojo contactPojo){
        Contact contact =new Contact();
        if(contactPojo.getContactId()!=null){
            contact.setContactId(contact.getContactId());
        }
        contact.setContactname(contactPojo.getContactname());
        contact.setContactemail(contactPojo.getContactemail());
        contact.setContactsubject(contactPojo.getContactsubject());
        contact.setContactmessage(contactPojo.getContactmessage());


        contactRepo.save(contact);
       return "created";
    }

    @Override
    public List<Contact> fetchAll() {
        return this.contactRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        contactRepo.deleteById(id);

    }

//    @Override
//
//    public Contact fetchById(Integer id) {
//        Contact contact= contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Couldnot find"));
//        contact = Contact.builder()
//                .contactId(contact.getContactId())
//                .contactname(contact.getContactname())
//                .contactemail(contact.getContactemail())
//                .contactsubject(contact.getContactsubject())
//                .contactmessage(contact.getContactmessage())
//                .build();
//        return contact;
//    }

}
