package com.system.volleyball.Service;

import com.system.volleyball.Pojo.ContactPojo;
import com.system.volleyball.entity.Contact;

import java.util.List;

public interface ContactService {
    String save(ContactPojo contactPojo);

    List<Contact> fetchAll();

    void deleteById(Integer id);
}
