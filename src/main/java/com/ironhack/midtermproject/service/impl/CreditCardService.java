package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.service.interfaces.ICreditCardService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService implements ICreditCardService {

//    @Autowired
//    private CreditCardRepository creditCardRepository;
//
//    public CreditCard store(CreditCard creditCard) {
//        Optional<Account> optionalCreditCard = creditCardRepository.findById(creditCard.getId());
//        if(optionalCreditCard.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Credit card account with id " + creditCard.getId() + "already exists.");
//
//        return creditCardRepository.save(creditCard);
//    }
}
