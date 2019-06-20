package pl.mbierut.services;

import org.springframework.stereotype.Service;

@Service
public class ReverseDate {
    String reverse(String date) {
        if (date.length() != 10){
            return date;
        }
        return date.substring(8, 10) + "-" + date.substring(5, 7) + "-" + date.substring(0, 4);
    }
}
