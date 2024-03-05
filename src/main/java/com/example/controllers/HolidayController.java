package com.example.controllers;


import com.example.model.Holiday;
import com.example.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayRepository holidayRepository;
    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model){
        if(null != display && display.equals("all")){
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        } else if (null != display && display.equals("festival")) {
            model.addAttribute("festival", true);
        } else if (null != display && display.equals("federal")) {
            model.addAttribute("federal", true);
        }
        Iterable<Holiday> holidays = holidayRepository.findAll();
        List<Holiday> holidaysList = StreamSupport.stream(holidays.spliterator(),false)
                .toList();
        Holiday.Type[] types = Holiday.Type.values();
        for(Holiday.Type type: types){
            model.addAttribute(type.toString(),
                    (holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).
                            collect(Collectors.toList())));
        }

        return "holidays";
    }
}
