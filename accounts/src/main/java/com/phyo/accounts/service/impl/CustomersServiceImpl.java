package com.phyo.accounts.service.impl;

import com.phyo.accounts.dto.AccountsDto;
import com.phyo.accounts.dto.CardsDto;
import com.phyo.accounts.dto.CustomerDetailsDto;
import com.phyo.accounts.dto.LoansDto;
import com.phyo.accounts.entity.Accounts;
import com.phyo.accounts.entity.Customer;
import com.phyo.accounts.exception.ResourceNotFoundException;
import com.phyo.accounts.mapper.AccountsMapper;
import com.phyo.accounts.mapper.CustomerMapper;
import com.phyo.accounts.repository.AccountsRepository;
import com.phyo.accounts.repository.CustomerRepository;
import com.phyo.accounts.service.ICustomersService;
import com.phyo.accounts.service.client.CardsFeignClient;
import com.phyo.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

        private AccountsRepository accountsRepository;
        private CustomerRepository customerRepository;
        private CardsFeignClient cardsFeignClient;
        private LoansFeignClient loansFeignClient;

        /**
         * @param mobileNumber - Input Mobile Number
         * @return Customer Details based on a given mobileNumber
         */
        @Override
        public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
            Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
            );
            Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
            );

            CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
            customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

            ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
            if(null != loansDtoResponseEntity) {
                customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
            }

            ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
            if(null!= cardsDtoResponseEntity) {
                customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
            }

            return customerDetailsDto;
    }
}
