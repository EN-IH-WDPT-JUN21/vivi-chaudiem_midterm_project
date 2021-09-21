package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.service.interfaces.ICheckingService;
import org.springframework.stereotype.Service;

@Service
public class CheckingService implements ICheckingService {
//    @Autowired
//    private CheckingRepository checkingRepository;
//
//
//    // DTO instead
//    public Checking store(CheckingDTO checkingDTO) {
//        Optional<Account> optionalChecking = checkingRepository.findById(checkingDTO.getId());
//        if(optionalChecking.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Checking account with id " + checkingDTO.getId() + "already exists.");
//
//        // Create a student checking account if the account holder is younger than 24
//        int age = calculateAge(checkingDTO.getAccountHolder().getDateOfBirth(), LocalDate.now());
//        if(age < 24) {
//            Checking studentChecking = new Checking(checkingDTO.getId(), checkingDTO.getSecretKey(), checkingDTO.getCreationDate(), CheckingType.STUDENT_CHECKING, checkingDTO.getStatus());
//            return checkingRepository.save(studentChecking);
//        } else {
//            Checking checking = new Checking(checkingDTO.getId(), checkingDTO.getSecretKey(), checkingDTO.getCreationDate(), checkingDTO.getCheckingType(), checkingDTO.getStatus());
//            return checkingRepository.save(checking);
//        }
//    }
//
//    // Helper method to calculate the age of the account holder
//    public static int calculateAge(LocalDate dateOfBirth, LocalDate currentDate) {
//        if(dateOfBirth != null && currentDate != null) {
//            return Period.between(dateOfBirth, currentDate).getYears();
//        } else {
//            return 0;
//        }
//    }
}
