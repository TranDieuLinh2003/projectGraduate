package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.RankCustomer;
import com.example.filmBooking.repository.CustomerRepository;
import com.example.filmBooking.repository.RankCustomerRepository;
import com.example.filmBooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private RankCustomerRepository rankRepository;

    @Override
    public List<Customer> fillAll() {
        return repository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        customer.setCode("code_" + value);
        customer.setPoint(0);
        List<RankCustomer> listRank = rankRepository.findAll();
        listRank.sort((o1, o2) -> {
            return o1.getPoint().compareTo(o2.getPoint());
        });
        if (customer.getPoint() > listRank.get(listRank.size() - 1).getPoint()) {
            //chuyển thành  goi cao nhat
            customer.setRankCustomer(rankRepository.findById(listRank.get(listRank.size() - 1).getId()).get());
        } else {
            for (int i = 0; i < listRank.size(); i++) {
                if (customer.getPoint() >= listRank.get(i).getPoint() && customer.getPoint() < listRank.get(i + 1).getPoint()) {
                    customer.setRankCustomer(rankRepository.findById(listRank.get(i).getId()).get());
                    break;
                }
            }
        }
        return repository.save(customer);
    }


    //tự động cập nhật rank cho khách hàng
    @Scheduled(fixedRate = 86400000)
    public void autoCheckPoint() {
        List<RankCustomer> listRank = rankRepository.findAll();
        listRank.sort((o1, o2) -> {
            return o1.getPoint().compareTo(o2.getPoint());
        });
        for (Customer customer : repository.findAll()
        ) {
            if (customer.getPoint() > listRank.get(listRank.size() - 1).getPoint()) {
                //chuyển thành  goi cao nhat
                customer.setRankCustomer(rankRepository.findById(listRank.get(listRank.size() - 1).getId()).get());
            } else {
                for (int i = 0; i < listRank.size(); i++) {
                    if (customer.getPoint() >= listRank.get(i).getPoint() && customer.getPoint() < listRank.get(i + 1).getPoint()) {
                        customer.setRankCustomer(rankRepository.findById(listRank.get(i).getId()).get());
                        break;
                    }
                }
            }
        }
    }



    @Override
    public Customer update(String id, Customer customer) {
        Customer customerNew = findById(id);
        customerNew.setName(customer.getName());
//        customerNew.setPoint(customer.getPoint());
        customerNew.setPhoneNumber(customer.getPhoneNumber());
        customerNew.setEmail(customer.getEmail());
        customerNew.setPassword(customer.getPassword());
        return repository.save(customerNew);
    }

    @Override
    public Customer findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }



//


}
