package com.example.service;

import com.example.config.CustomProps;
import com.example.constants.ContactConstants;
import com.example.model.Contact;
import com.example.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CustomProps customProps;

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(ContactConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(savedContact.getContactId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
    public Page<Contact> findMessagesWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize = customProps.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sortDir.equals("asc") ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(ContactConstants.OPEN, pageable);
        return msgPage;
    }

    public boolean updateMessageStatus(int contactId) {
        boolean isUpdated = false;
        Optional<Contact> optionalContact = contactRepository.findById(contactId);

        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setStatus(ContactConstants.CLOSE);
            Contact updatedContact = contactRepository.save(contact);

             isUpdated = updatedContact.getUpdatedBy() != null;
        }

        return isUpdated;
    }
}
